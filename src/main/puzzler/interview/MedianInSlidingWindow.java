package puzzler.interview;

import java.util.Collections;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class MedianInSlidingWindow {

    private final PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
    private final PriorityQueue<Integer> right = new PriorityQueue<>();

    //left : 0
    //right: 1
    private void addNum(int val) {
        left.add(val);
        right.add(left.poll());

        if (right.size() > left.size()) {
            left.add(right.poll());
        }
    }

    private double findMedian() {
        if ((left.size() + right.size()) % 2 == 0) {
            return (left.peek() + right.peek()) / 2.0;
        }
        return left.peek();
    }

    @Test()
    public void test() {

        MedianInSlidingWindow mf = new MedianInSlidingWindow();

        mf.addNum(1);
        mf.addNum(2);
        assertEquals(mf.findMedian(), 1.5);
        mf.addNum(3);
        assertEquals(mf.findMedian(), 2.0);
        mf.addNum(2);
        assertEquals(mf.findMedian(), 2.0);
        mf.addNum(6);
        assertEquals(mf.findMedian(), 2.0);
        mf.addNum(6);
        assertEquals(mf.findMedian(), (2 + 3) / 2.0);
        mf.addNum(0);
        assertEquals(mf.findMedian(), 2.0);
        mf.addNum(5);
        assertEquals(mf.findMedian(), (2 + 3) / 2.0);
    }

    @Test()
    public void test2() {

        MedianInSlidingWindow mf = new MedianInSlidingWindow();

        mf.addNum(40);
        assertEquals(mf.findMedian(), 40.0);
        mf.addNum(12);
        assertEquals(mf.findMedian(), 26.0);
        mf.addNum(16);
        assertEquals(mf.findMedian(), 16.0);
    }

    @Test()
    public void test3() {

        MedianInSlidingWindow mf = new MedianInSlidingWindow();

        mf.addNum(-1);
        assertEquals(mf.findMedian(), -1.0);
        mf.addNum(-2);
        assertEquals(mf.findMedian(), -1.5);
        mf.addNum(-3);
        assertEquals(mf.findMedian(), -2.0);
    }
}
