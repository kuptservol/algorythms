package java_.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class CompletableFututreIntrinisticsTest {

    @org.junit.Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(10000);
                        System.out.println("Finished");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                },
                executorService);

        future.get();
    }
}
