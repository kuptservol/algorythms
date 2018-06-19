package java_.concurrent.queue;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

/**
 * @author Sergey Kuptsov
 */
public class LinkedBlockingQueueOneSyncBlock {

    private final LinkedList<Integer> queue = new LinkedList<>();
    private final int size;

    public LinkedBlockingQueueOneSyncBlock(int size) {
        this.size = size;
    }

    public void add(int value) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == size)
                queue.wait();

            queue.addFirst(value);
            queue.notify();
        }
    }

    public int poll() throws InterruptedException {
        int val;
        synchronized (queue) {
            if (queue.size() == 0) {
                queue.wait();
            }
            val = queue.pollLast();
            queue.notify();
        }

        return val;
    }

    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueueOneSyncBlock queue = new LinkedBlockingQueueOneSyncBlock(2);

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
