package ru.skuptsov.patterns.structural.flyweight;

import java.util.concurrent.*;

/**
 * Created by Сергей on 25.04.2016.
 */
public class ThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
            System.out.println("Creating thread only once");
            return new Thread(r);
        });

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(i);
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }


}
