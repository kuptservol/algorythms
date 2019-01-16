package puzzler.leetcode.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * find the minimum number of conference rooms required.
 */
public class MeetingRoomsII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
              //  {intervals(0, 30, 5, 10, 15, 20), 2},
               // {intervals(7, 10, 2, 4), 1},
                {intervals(0, 13, 13, 15), 1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(Interval[] intervals, int result) {
        assertEquals(sortChronologicallyMinRoomsNumber(intervals), result);
        assertEquals(findNearestRoomWithHeap(intervals), result);
    }

    public int findNearestRoomWithHeap(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int maxRoomsNum = 0;

        PriorityQueue<Integer> nextRoomBecomeAvailable = new PriorityQueue<>(intervals.length, Comparator.comparingInt(o -> o));
        Arrays.sort(intervals, Comparator.comparingInt(o -> o.start));

        for (Interval interval : intervals) {
            Integer nextRoomBecomeAvailableAt = nextRoomBecomeAvailable.peek();
            if (nextRoomBecomeAvailableAt != null && nextRoomBecomeAvailableAt <= interval.start) {
                nextRoomBecomeAvailable.poll();
            }

            nextRoomBecomeAvailable.add(interval.end);

            maxRoomsNum = Math.max(maxRoomsNum, nextRoomBecomeAvailable.size());
        }

        return maxRoomsNum;
    }

    public int sortChronologicallyMinRoomsNumber(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int currParallel = 0;
        int maxParallel = 0;
        List<Point> points = new ArrayList<>();
        Arrays.stream(intervals).forEach(
                val -> {
                    points.add(new Point(val.start, true));
                    points.add(new Point(val.end, false));
                }
        );

        Collections.sort(points);

        for (Point p : points) {
            if (p.start) {
                currParallel++;
            } else {
                currParallel--;
            }

            maxParallel = Math.max(maxParallel, currParallel);
        }

        return maxParallel;
    }

    public class Point implements Comparable<Point> {
        int value;
        boolean start;

        public Point(int value, boolean start) {
            this.value = value;
            this.start = start;
        }

        @Override
        public int compareTo(Point o) {
            int diff = value - o.value;
            if (diff != 0) {
                return diff;
            } else {
                if (start) return 1;
                else
                    return -1;
            }
        }
    }

    public static Interval[] intervals(Integer... ranges) {
        Interval[] intervals = new Interval[ranges.length / 2];

        for (int i = 0; i < ranges.length; i += 2) {
            intervals[i / 2] = new Interval(ranges[i], ranges[i + 1]);
        }

        return intervals;
    }

    public static class Interval {
        int start;
        int end;

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }
}
