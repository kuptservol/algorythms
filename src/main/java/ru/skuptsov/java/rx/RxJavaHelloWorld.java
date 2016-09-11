package ru.skuptsov.java.rx;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.observables.AsyncOnSubscribe;

/**
 * @author Sergey Kuptsov
 * @since 11/09/2016
 */
public class RxJavaHelloWorld {

    public static void main(String[] args) {
        //hello("Ben", "George");
//        getSyncObservables().subscribe(System.out::println);
        getASyncObservables().skip(10).take(5).subscribe(System.out::println);
    }

    public static void hello(String... names) {
        Observable.from(names).subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }
        });
    }

    public static Observable<String> getASyncObservables() {
        Observable<String> observable = Observable.create(new AsyncOnSubscribe<String, String>() {
            @Override
            protected String generateState() {
                return "state";
            }

            @Override
            protected String next(String state, long requested, Observer<Observable<? extends String>> observer) {
                observer.onNext(Observable.create(subscriber -> {
                    for (int i = 0; i < 10; i++) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext("value_" + i);
                        }
                    }

                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onCompleted();
                    }
                }));
                return "state";
            }
        });

        return observable;
    }

    public static Observable<String> getSyncObservables() {
        return Observable.create(subscriber -> {
            for (int i = 0; i < 10; i++) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext("value_" + i);
                }
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        });
    }
}
