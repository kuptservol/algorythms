package puzzler.leetcode.heap;

import java.util.Arrays;
import java.util.Comparator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...]
 *         (si < ei), determine if a person could attend all meetings.
 */
public class MeetingRooms {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intervals(0, 30, 5, 10, 15, 20), false},
                {intervals(7, 10, 2, 4), true},
                {intervals(2, 4, 2, 4), false},
        };
    }

    @Test(dataProvider = "testData")
    public void test(Interval[] intervals, boolean result) {
        assertEquals(canAttendMeetings(intervals), result);
    }

    public boolean canAttendMeetings(Interval[] intervals) {
        if (intervals.length == 0) {
            return true;
        }

        //first sort by start then find if overlapping NlogN
        Arrays.sort(intervals, Comparator.comparingInt(o -> o.start));

        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i].end >= intervals[i + 1].start)
                return false;
        }

        return true;
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

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }
}
