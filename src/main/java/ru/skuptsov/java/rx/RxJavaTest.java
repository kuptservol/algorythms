package ru.skuptsov.java.rx;

import org.testng.annotations.Test;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Sergey Kuptsov
 * @since 11/09/2016
 */
public class RxJavaTest {

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
}
