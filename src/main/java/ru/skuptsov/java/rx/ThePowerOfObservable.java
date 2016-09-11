package ru.skuptsov.java.rx;

import rx.Observable;
import rx.Subscriber;
import rx.observables.BlockingObservable;

import java.util.concurrent.CompletableFuture;

/**
 * @author Sergey Kuptsov
 * @since 11/09/2016
 */
public class ThePowerOfObservable {

    public static void main(String[] args) {
        processTopUsersWithBlockedObservable(getAllUsersSync());
        System.out.println("-----------------");
        processTopUsersWithBlockedObservable(getAllUsersAsync().toBlocking());
    }

    private static void processTopUsersWithBlockedObservable(BlockingObservable<String> stringBlockingObservable) {
        stringBlockingObservable.forEach(v ->
        {
            System.out.println("Start processing : " + v);
            System.out.println("Finished processing : " + v);
        });
    }

    private static void processTopUsersWithBlockedObservable(Observable<String> observable) {
        observable
                .take(3)
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("Start processing : " + o);
                        System.out.println("Finished processing : " + o);
                    }
                });
    }

    public static Observable<String> getAllUsersSync() {
        return Observable.create(subscriber -> {
            for (int i = 0; i < 100; i++) {
                if (!subscriber.isUnsubscribed()) {
                    String user = "user" + i;
                    System.out.println("Heavy system thinking!");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Returning " + user + ", can process already!");
                    subscriber.onNext(user);
                }
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getAllUsersAsync() {
        return Observable.create(subscriber -> {
            CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 100; i++) {
                    if (!subscriber.isUnsubscribed()) {
                        String user = "user" + i;
                        System.out.println("Heavy system thinking!");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Returning " + user + ", can process already!");
                        subscriber.onNext(user);
                    }
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            });
        });
    }
}
