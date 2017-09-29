package java_.javarx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.testng.annotations.Test;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author Sergey Kuptsov
 * @since 11/09/2016
 */
public class BatchWithTimeoutOnRxJavaTest {

    @Test
    public void simple() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Next one!");
                subscriber.onCompleted();
            }
        });


        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String o) {
                System.out.println(o);
            }
        };

        observable.subscribe(subscriber);
    }

    @Test
    public void lambda() {
        Observable.just("Next one")
                .subscribe(System.out::println);
    }

    @Test
    public void exceptions() {
        Observable.just("1", "2")
                .map(this::anotherPotentialException)
                .doOnError(s -> System.out.println("nothing"))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Exception!");
                    }
                });
    }

    private String anotherPotentialException(String s) {
        if (s.equals("2"))
            return "2";
        else
            throw new RuntimeException();
    }

    @Test
    public void withBackPressure() {
        Observable.just("1", "2")
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onStart() {
                        request(1);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                        request(1);
                    }
                });
    }

    @Test
    public void bufferWithParallelization() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(
                5,
                new ThreadFactoryBuilder().setDaemon(true).setNameFormat("PushClientQueueProcessor-%d").build());

        Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("Observe thread" + Thread.currentThread().getName());
                for (int i = 0; i < 100; i++) {
                    subscriber.onNext(i);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                subscriber.onCompleted();
            }
        })
                .observeOn(Schedulers.from(executorService))
                .buffer(5, TimeUnit.SECONDS, 4)
                .observeOn(Schedulers.from(executorService))
                .flatMap(l -> Observable.just(l)
                        .observeOn(Schedulers.from(executorService))
                        .subscribeOn(Schedulers.from(executorService))
                        .observeOn(Schedulers.from(executorService))
                        .doOnNext(s -> {
                                    System.out.println("Subscribe thread" + Thread.currentThread().getName());
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println(s);
                                }
                        )
                )
                .observeOn(Schedulers.from(executorService))
                .subscribe();

        Thread.sleep(50000);
    }
}
