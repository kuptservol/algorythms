package ru.skuptsov.java.benchmark;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ForwardingQueue;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

/**
 * @author Sergey Kuptsov
 * @since 20/01/2017
 */
public class BatchWithTimeoutOnRxJava extends BaseBatchWithTimeout {

    private final static ForwardingQueue<Integer> eventsQueue = EvictingQueue.create(PUSH_QUEUE_MAX_SIZE);

    public static void main(String[] args) throws InterruptedException {
        long time = System.nanoTime();
        batchWithTimeoutWithRX();
        System.out.println("Time rx : " + (System.nanoTime() - time) / (1000 * 1000) + " ms");
    }

    public static void batchWithTimeoutWithRX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(EVENTS_COUNT);

        Observable<List<Integer>> pushesToSend = Observable.<Integer>create(
                observer -> {
                    Integer pushEvent = eventsQueue.poll();
                    if (pushEvent != null) {
                        observer.onNext(pushEvent);
                    }
                    observer.onCompleted();
                })
                .repeatWhen(observable -> observable
                        .delay(1, MICROSECONDS)
                        .observeOn(Schedulers.from(executor)))
                .buffer(PUSH_PERIOD,
                        PUSH_PERIOD_TIMEUNIT,
                        PUSH_BATCH_SIZE)
                .flatMap(data -> Observable.just(data)
                        .subscribeOn(Schedulers.from(executor))
                        .doOnNext(s -> {
                            if (s.size() == 0) {
                                return;
                            }
                            subscribe(latch, s);
                        })
                );

        pushesToSend.subscribe();

        for (int i = 0; i < EVENTS_COUNT; i++) {
            eventsQueue.offer(i);
        }

        latch.await();
    }
}
