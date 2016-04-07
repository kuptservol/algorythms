package ru.skuptsov.patterns.observer;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Sergey Kuptsov
 * @since 07/04/2016
 */
public class Observer {


    private static class EventSource extends Observable implements Runnable {

        @Override
        public void run() {
            while (true) {
                String text = new Scanner(System.in).next();
                setChanged();
                notifyObservers(text);
            }

        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventSource eventSource = new EventSource();

        eventSource.addObserver((Observable o, Object arg) -> System.out.println("Received input " + arg));

        CompletableFuture.runAsync(eventSource).get();

    }
}

