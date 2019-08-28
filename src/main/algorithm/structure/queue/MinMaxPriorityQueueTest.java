package algorithm.structure.queue;

import java.util.Random;

import com.google.common.collect.MinMaxPriorityQueue;
//import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

/**
 * @author Sergey Kuptsov
 * @since 01/05/2016
 */
public class MinMaxPriorityQueueTest {

    public static void main(String[] args) {

        Random random = new Random();

        int[] massive = new int[1000000];
        for (int i = 0; i < massive.length; i++) {
            massive[i] = random.nextInt(1000000);
        }

//        System.out.println(ObjectSizeCalculator.getObjectSize(new Object()));

        MinMaxPriorityQueue<Integer> minMaxPriorityQueue = MinMaxPriorityQueue
                .maximumSize(1000)
                .create();

        for (int i = 0; i < massive.length; i++) {
            minMaxPriorityQueue.add(massive[i]);
        }

        for (int i = 0; i < 100; i++) {
            System.out.print(" " + minMaxPriorityQueue.poll());
        }
    }
}
