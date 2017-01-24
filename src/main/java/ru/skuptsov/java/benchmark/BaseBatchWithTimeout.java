package ru.skuptsov.java.benchmark;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author Sergey Kuptsov
 * @since 20/01/2017
 */
public class BaseBatchWithTimeout {

    public static final int N_THREADS = 10;
    public final static ExecutorService executor = newFixedThreadPool(
            N_THREADS,
            new ThreadFactoryBuilder().setDaemon(true).setNameFormat("PushClientQueueProcessor-%d").build()
    );
    public static final long PUSH_PERIOD = 100;
    public static final int EVENTS_COUNT = 1000000;
    public static final int PUSH_QUEUE_MAX_SIZE = EVENTS_COUNT;
    public static final int PUSH_BATCH_SIZE = 10;
    public static final TimeUnit PUSH_PERIOD_TIMEUNIT = TimeUnit.MILLISECONDS;

    protected static void subscribe(CountDownLatch latch, List<Integer> s) {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < s.size(); i++) {
            latch.countDown();
        }
    }
}
