package puzzler.leetcode.math;

import java.util.LinkedList;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
 * <p>
 * MovingAverage m = new MovingAverage(3);
 * m.next(1) = 1
 * m.next(10) = (1 + 10) / 2
 * m.next(3) = (1 + 10 + 3) / 3
 * m.next(5) = (10 + 3 + 5) / 3
 */
public class MovingAverage {

    int size;
    LinkedList<Integer> list = new LinkedList<>();

    /**
     * Initialize your data structure here.
     */
    public MovingAverage(int size) {
        this.size = size;
    }

    public double next(int val) {
        if (list.size() == size) {
            list.removeFirst();
        }

        list.add(val);

        return avg(list);
    }

    private double avg(LinkedList<Integer> list) {
        return list.stream().reduce((a, b) -> a + b).orElse(0) / (double) list.size();
    }

    @Test
    public static void test() {
        MovingAverage avg = new MovingAverage(3);
        assertEquals(1d, avg.next(1));
        assertEquals((1 + 10) / 2d, avg.next(10));
        assertEquals((1 + 10 + 3) / 3d, avg.next(3));
        assertEquals((10 + 3 + 5) / 3d, avg.next(5));
    }
}
