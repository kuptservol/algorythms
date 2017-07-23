package puzzler.leetcode.stringarray;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.common.base.Objects;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static puzzler.leetcode.stringarray.InsertInterval.Interval.interval;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * <p>
 * Given a set of non-overlapping & sorted intervals,
 * insert a new interval into the intervals (merge if necessary).
 */
public class InsertInterval {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(interval(1, 3), interval(6, 9)), interval(2, 5), of(interval(1, 5), interval(6, 9))},
                {of(interval(1, 2), interval(3, 5), interval(6, 7), interval(8, 10), interval(12, 16)), interval(4, 9), of(interval(1, 2), interval(3, 10), interval(12, 16))}
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Interval> givenIntervals, Interval toMerge, List<Interval> test) {
        assertEquals(mergeInterval(givenIntervals, toMerge), test);
    }

    private List<Interval> mergeInterval(List<Interval> givenIntervals, Interval toMerge) {
        TreeIntervalSet treeIntervalSet = new TreeIntervalSet(givenIntervals
                .stream()
                .collect(Collectors.toMap(interval -> interval.start,
                        interval -> interval(interval.start, interval.end),
                        (a, b) -> a,
                        TreeMap::new)));

        treeIntervalSet.add(toMerge);

        return treeIntervalSet.getIntervalSet().entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    final static class TreeIntervalSet {
        TreeMap<Integer, Interval> intervalSet = new TreeMap<>();

        public TreeIntervalSet(TreeMap<Integer, Interval> intervalSet) {
            this.intervalSet = intervalSet;
        }

        /**
         * O(NlogN)
         */
        void add(Interval interval) {
            int newIntStart, newIntEnd;

            Interval sLeftClosest = intervalSet.floorEntry(interval.start).getValue();
            if (sLeftClosest == null || sLeftClosest.end < interval.start) {
                newIntStart = interval.start;
            } else {
                newIntStart = sLeftClosest.start;
            }

            Interval eLeftClosest = intervalSet.floorEntry(interval.end).getValue();
            if (eLeftClosest == null || eLeftClosest.end < interval.end) {
                newIntEnd = interval.end;
            } else {
                newIntEnd = eLeftClosest.end;
            }

            intervalSet.subMap(newIntStart, newIntEnd).clear();

            intervalSet.put(newIntStart, interval(newIntStart, newIntEnd));
        }

        public TreeMap<Integer, Interval> getIntervalSet() {
            return intervalSet;
        }
    }

    final static class Interval implements Comparable<Interval> {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        static Interval interval(int start, int end) {
            return new Interval(start, end);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Interval interval = (Interval) o;
            return start == interval.start &&
                    end == interval.end;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(start, end);
        }

        @Override
        public int compareTo(Interval that) {
            if (this.start < that.start) {
                return -1;
            } else if (this.start > that.start) {
                return 1;
            }

            if (this.end < that.end) {
                return -1;
            } else if (this.end > that.end) {
                return 1;
            }
            return 0;
        }
    }
}
