package java_.concurrent.queue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
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

        queue.add(2);
        queue.add(1);

        System.out.println(queue.poll());
        System.out.println(queue.poll());

        queue.add(3);

        System.out.println(queue.poll());
        System.out.println(queue.poll());

    }
}
