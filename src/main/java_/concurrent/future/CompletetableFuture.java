package java_.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Sergey Kuptsov
 */
public class CompletetableFuture {

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("1");
//        try {
//            CompletableFuture.allOf(of(
//                    CompletableFuture.runAsync(new RunnableOk()),
//                    CompletableFuture.runAsync(new RunnableOk()))
//                    .toArray(new CompletableFuture[]{}))
//                    .get(1, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("2");
//        try {
//            CompletableFuture.allOf(of(
//                    CompletableFuture.runAsync(new RunnableWithTimeout()),
//                    CompletableFuture.runAsync(new RunnableOk()))
//                    .toArray(new CompletableFuture[]{}))
//                    .get(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            System.out.println("InterruptedException " + e.getMessage());
//        } catch (ExecutionException e) {
//            System.out.println("Execution Exception " + e.getMessage());
//        } catch (TimeoutException e) {
//            System.out.println("Timeout Exception " + e.getMessage());
//        }
//
//        System.out.println("3");
//        //exception lost
//
//        try {
//            CompletableFuture.allOf(of(
//                    CompletableFuture.runAsync(new RunnableWithException()),
//                    CompletableFuture.runAsync(new RunnableOk()))
//                    .toArray(new CompletableFuture[]{}))
//                    .get(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            System.out.println("InterruptedException " + e.getMessage());
//        } catch (ExecutionException e) {
//            System.out.println("Execution Exception " + e.getMessage());
//        } catch (TimeoutException e) {
//            System.out.println("Timeout Exception " + e.getMessage());
//        }
//
//        System.out.println("4");
//
//        try {
//            CompletableFuture.allOf(of(
//                    CompletableFuture.runAsync(new RunnableWithException()),
//                    CompletableFuture.runAsync(new RunnableOk()))
//                    .toArray(new CompletableFuture[]{}))
//                    .whenComplete((res, ex) -> System.out.println("Comleted with exception : " + ex.getMessage()))
//                    .get(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            System.out.println("InterruptedException " + e.getMessage());
//        } catch (ExecutionException e) {
//            System.out.println("Execution Exception " + e.getMessage());
//        } catch (TimeoutException e) {
//            System.out.println("Timeout Exception " + e.getMessage());
//        }
//
//        System.out.println("5");
//
//        // NPE here!!!!
//        try {
//            CompletableFuture.allOf(of(
//                    CompletableFuture.runAsync(new RunnableOk()),
//                    CompletableFuture.runAsync(new RunnableOk()))
//                    .toArray(new CompletableFuture[]{}))
//                    .whenComplete((res, ex) -> System.out.println("Completed with exception : " + ex.getMessage()))
//                    .get(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            System.out.println("InterruptedException " + e.getMessage());
//        } catch (ExecutionException e) {
//            System.out.println("Execution Exception " + e.getMessage());
//        } catch (TimeoutException e) {
//            System.out.println("Timeout Exception " + e.getMessage());
//        }
//
//        System.out.println("5.1");
//
//        // NPE here!!!!
//        try {
//            CompletableFuture.allOf(of(
//                    CompletableFuture.runAsync(new RunnableOk()),
//                    CompletableFuture.runAsync(new RunnableOk()))
//                    .toArray(new CompletableFuture[]{}))
//                    .whenCompleteAsync((res, ex) -> System.out.println("Completed with exception : " + ex.getMessage()))
//                    .get(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            System.out.println("InterruptedException " + e.getMessage());
//        } catch (ExecutionException e) {
//            System.out.println("Execution Exception " + e.getMessage());
//        } catch (TimeoutException e) {
//            System.out.println("Timeout Exception " + e.getMessage());
//        }
//
//        System.out.println("6");
//
//        try {
//            CompletableFuture.allOf(of(
//                    CompletableFuture.runAsync(new RunnableOk()),
//                    CompletableFuture.runAsync(new RunnableWithTimeout()))
//                    .toArray(new CompletableFuture[]{}))
//                    .whenComplete((res, ex) -> System.out.println("Comleted with exception : " + ex.getMessage()))
//                    .get(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            System.out.println("InterruptedException " + e.getMessage());
//        } catch (ExecutionException e) {
//            System.out.println("Execution Exception " + e.getMessage());
//        } catch (TimeoutException e) {
//            System.out.println("Timeout Exception " + e.getMessage());
//        }

//        System.out.println("7");
//        System.out.println("Do not miss every future exception");
//
//        of(
//                CompletableFuture.runAsync(new RunnableWithTimeout()),
//                CompletableFuture.runAsync(new RunnableWithException())
//        ).forEach(future -> {
//                    try {
//                        future.get(3, TimeUnit.SECONDS);
//                            } catch (InterruptedException e) {
//                                System.out.println("InterruptedException " + e.getMessage());
//                            } catch (ExecutionException e) {
//                                System.out.println("Execution Exception " + e.getMessage());
//                            } catch (TimeoutException e) {
//                                System.out.println("Timeout Exception " + e.getMessage());
//                            }
//                        }
//                );

        System.out.println("8");
        System.out.println("Do not miss every future exception");

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> new CallableWithException().call())
                .whenComplete((r, e) -> {
                    if (e != null) {
                                System.out.println("Execution Exception " + e.getMessage());
                            }
                        }
                );


        /// wait for all futures - simulate program running
        Thread.sleep(5000);

        System.out.println(stringCompletableFuture);
    }

    static class RunnableWithTimeout implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("Running RunnableWithTimeout");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class RunnableWithException implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Running RunnableWithException");
            throw new RuntimeException("RunnableWithException");
        }
    }

    static class CallableWithException implements Callable {

        @Override
        public String call() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Running RunnableWithException");
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
