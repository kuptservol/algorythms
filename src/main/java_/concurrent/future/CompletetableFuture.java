package java_.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.google.common.collect.ImmutableList.of;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class CompletetableFuture {

    public static void main(String[] args) {
        System.out.println("1");
        try {
            CompletableFuture.allOf(of(
                    CompletableFuture.runAsync(new RunnableOk()),
                    CompletableFuture.runAsync(new RunnableOk()))
                    .toArray(new CompletableFuture[]{}))
                    .get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("2");
        try {
            CompletableFuture.allOf(of(
                    CompletableFuture.runAsync(new RunnableWithTimeout()),
                    CompletableFuture.runAsync(new RunnableOk()))
                    .toArray(new CompletableFuture[]{}))
                    .get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Execution Exception " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Timeout Exception " + e.getMessage());
        }

        System.out.println("3");
        //exception lost

        try {
            CompletableFuture.allOf(of(
                    CompletableFuture.runAsync(new RunnableWithException()),
                    CompletableFuture.runAsync(new RunnableOk()))
                    .toArray(new CompletableFuture[]{}))
                    .get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Execution Exception " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Timeout Exception " + e.getMessage());
        }

        System.out.println("4");

        try {
            CompletableFuture.allOf(of(
                    CompletableFuture.runAsync(new RunnableWithException()),
                    CompletableFuture.runAsync(new RunnableOk()))
                    .toArray(new CompletableFuture[]{}))
                    .whenComplete((res, ex) -> System.out.println("Comleted with exception : " + ex.getMessage()))
                    .get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Execution Exception " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Timeout Exception " + e.getMessage());
        }

        System.out.println("5");

        // NPE here!!!!
        try {
            CompletableFuture.allOf(of(
                    CompletableFuture.runAsync(new RunnableOk()),
                    CompletableFuture.runAsync(new RunnableOk()))
                    .toArray(new CompletableFuture[]{}))
                    .whenComplete((res, ex) -> System.out.println("Completed with exception : " + ex.getMessage()))
                    .get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Execution Exception " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Timeout Exception " + e.getMessage());
        }

        System.out.println("5.1");

        // NPE here!!!!
        try {
            CompletableFuture.allOf(of(
                    CompletableFuture.runAsync(new RunnableOk()),
                    CompletableFuture.runAsync(new RunnableOk()))
                    .toArray(new CompletableFuture[]{}))
                    .whenCompleteAsync((res, ex) -> System.out.println("Completed with exception : " + ex.getMessage()))
                    .get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Execution Exception " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Timeout Exception " + e.getMessage());
        }

        System.out.println("6");

        try {
            CompletableFuture.allOf(of(
                    CompletableFuture.runAsync(new RunnableOk()),
                    CompletableFuture.runAsync(new RunnableWithTimeout()))
                    .toArray(new CompletableFuture[]{}))
                    .whenComplete((res, ex) -> System.out.println("Comleted with exception : " + ex.getMessage()))
                    .get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Execution Exception " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Timeout Exception " + e.getMessage());
        }

        System.out.println("7");
        System.out.println("Do not miss every future exception");

        of(
                CompletableFuture.runAsync(new RunnableWithException()),
                CompletableFuture.runAsync(new RunnableWithTimeout()))
                .forEach(future -> {
                            try {
                                future.get(1, TimeUnit.SECONDS);
                            } catch (InterruptedException e) {
                                System.out.println("InterruptedException " + e.getMessage());
                            } catch (ExecutionException e) {
                                System.out.println("Execution Exception " + e.getMessage());
                            } catch (TimeoutException e) {
                                System.out.println("Timeout Exception " + e.getMessage());
                            }
                        }
                );

    }

    static class RunnableWithTimeout implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class RunnableWithException implements Runnable {

        @Override
        public void run() {
            throw new RuntimeException("RunnableWithException");
        }
    }

    static class RunnableOk implements Runnable {

        @Override
        public void run() {
            System.out.println("Norm");
        }
    }
}
