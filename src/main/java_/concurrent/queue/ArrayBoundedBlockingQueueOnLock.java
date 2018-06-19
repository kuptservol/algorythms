package java_.concurrent.queue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Sergey Kuptsov
 */
public class ArrayBoundedBlockingQueueOnLock<T> {

    private final int[] queue;
    int count = 0;
    private final int size;
    private final ReentrantLock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();
    int putIndex = -1;
    int getIndex = -1;

    public ArrayBoundedBlockingQueueOnLock(int size) {
        this.queue = new int[size];
        this.size = size;
    }

    public void add(int value) throws InterruptedException {

        lock.lock();

        try {
            while (count == size)
                notFull.await();

            if (++putIndex == queue.length) {
                putIndex = 0;
            }

            queue[putIndex] = value;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int poll() throws InterruptedException {
        lock.lock();

        int val;
        try {
            if (count == 0)
                notEmpty.await();

            if (++getIndex == queue.length) {
                getIndex = 0;
            }

            val = queue[getIndex];
            count--;
            notFull.signal();
        } finally {
            lock.unlock();
        }

        return val;
    }

    public int poll(TimeUnit timeUnit, int time) {
        throw new NotImplementedException();
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayBoundedBlockingQueueOnLock queue = new ArrayBoundedBlockingQueueOnLock(2);


        queue.add(1);
        queue.add(2);

        System.out.println(queue.poll());
        System.out.println(queue.poll());

        queue.add(3);

        System.out.println(queue.poll());

        CompletableFuture.runAsync(() -> {
            try {
                System.out.println(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture.runAsync(() -> {
            try {
                queue.add(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        queue.add(5);
        queue.add(6);

        CompletableFuture.runAsync(() -> {
            try {
                queue.add(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture.runAsync(() -> {
            try {
                System.out.println(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
