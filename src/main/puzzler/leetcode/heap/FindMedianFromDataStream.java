package puzzler.leetcode.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value.
 *         So the median is the mean of the two middle value.
 *         <p>
 *         For example,
 *         [2,3,4], the median is 3
 *         <p>
 *         [2,3], the median is (2 + 3) / 2 = 2.5
 */
public class FindMedianFromDataStream {

    /**
     * The idea is to have two heaps of the same size +-1 element -
     * one wil contains max element of left size of list of elements if they are sorted
     * second - min element of right size of list of elements if they are sorted
     * <p>
     * O(1) for get
     * log(N) for insert
     */
    class MedianFinderOnTwoHeaps {

        PriorityQueue<Integer> leftSide = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> rightSide = new PriorityQueue<>();

        public void addNum(int num) {
            leftSide.offer(num);
            rightSide.offer(leftSide.poll());

            if (rightSide.size() > leftSide.size()) {
                leftSide.offer(rightSide.poll());
            }
        }

        public double findMedian() {
            if (leftSide.size() == rightSide.size()) {
                return (leftSide.peek() + rightSide.peek()) / 2.0;
            } else {
                return leftSide.peek();
            }
        }
    }

    /**
     * Store sorted list with binary search for finding insert position
     * O(1) for get
     * log(N) to insert
     * passes!
     */
    class MedianFinderSortedBinSearchList {
        List<Integer> sortedList = new ArrayList<>();

        public MedianFinderSortedBinSearchList() {

        }

        public void addNum(int num) {
            int sortPos = sortedPos(0, sortedList.size() - 1, num);
            if (sortPos <= 0) {
                if (sortedList.size() == 0) {
                    sortedList.add(num);
                } else {
                    if (num <= sortedList.get(0)) {
                        sortedList.add(1, sortedList.get(0));
                        sortedList.set(0, num);
                    } else {
                        sortedList.add(1, num);
                    }
                }
            } else if (sortPos > sortedList.size() - 1) {
                if (num >= sortedList.get(sortedList.size() - 1)) {
                    sortedList.add(num);
                } else {
                    sortedList.add(sortedList.size() - 1, num);
                }
            } else {
                if (num <= sortedList.get(sortPos)) {
                    sortedList.add(sortPos, num);
                } else {
                    sortedList.add(sortPos + 1, num);
                }
            }
        }

        int sortedPos(int from, int to, int value) {
            if (sortedList.size() == 0) {
                return 0;
            }
            int mid = (to + from) / 2;

            if (from > to) {
                return from;
            }

            if (from < 0) {
                return 0;
            }

            if (to > sortedList.size() - 1) {
                return sortedList.size() - 1;
            }

            Integer midVal = sortedList.get(mid);
            if (midVal == value) {
                return mid;
            } else if (midVal < value) {
                return sortedPos(mid + 1, to, value);
            } else {
                return sortedPos(from, mid - 1, value);
            }
        }

        public double findMedian() {
            return sortedList.size() % 2 != 0
                    ? sortedList.get(sortedList.size() / 2)
                    : (sortedList.get(sortedList.size() / 2 - 1) + sortedList.get(sortedList.size() / 2)) / 2.0;
        }
    }

    @Test()
    public void test() {

//        MedianFinderSortedBinSearchList mf = new MedianFinderSortedBinSearchList();
        MedianFinderOnTwoHeaps mf = new MedianFinderOnTwoHeaps();

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
        assertEquals(mf.findMedian(), (2.0 + 3.0) / 2);
        mf.addNum(0);
        assertEquals(mf.findMedian(), 2.0);
        mf.addNum(5);
        assertEquals(mf.findMedian(), (2.0 + 3.0) / 2);
    }

    @Test()
    public void test2() {

//        MedianFinderSortedBinSearchList mf = new MedianFinderSortedBinSearchList();
        MedianFinderOnTwoHeaps mf = new MedianFinderOnTwoHeaps();

        mf.addNum(40);
        assertEquals(mf.findMedian(), 40.0);
        mf.addNum(12);
        assertEquals(mf.findMedian(), 26.0);
        mf.addNum(16);
        assertEquals(mf.findMedian(), 16.0);
    }

    @Test()
    public void test3() {

//        MedianFinderSortedBinSearchList mf = new MedianFinderSortedBinSearchList();
        MedianFinderOnTwoHeaps mf = new MedianFinderOnTwoHeaps();

        mf.addNum(-1);
        assertEquals(mf.findMedian(), -1.0);
        mf.addNum(-2);
        assertEquals(mf.findMedian(), -1.5);
        mf.addNum(-3);
        assertEquals(mf.findMedian(), -2.0);
    }
}
