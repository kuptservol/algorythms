package puzzler.leetcode.stringarray;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.common.base.Objects;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Range.closed;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 09/10/2016
 * Given a collection of intervals, merge all overlapping intervals.
 * <p>
 * For example,
 * Given [1,3],[2,6],[8,10],[15,18]
 * return [1,6],[8,10],[15,18].
 */
public class MergeIntervals {

    static Set<Range<Integer>> getOverlappedIntervalsWithTree(List<List<Integer>> intervals) {
        IntRangeSet intRangeSet = new IntRangeSet();

        intervals.forEach(interval -> intRangeSet.add(new IntRange(interval.get(0), interval.get(1))));

        return intRangeSet.getRanges()
                .stream()
                .map(intRange -> closed(intRange.getLeft(), intRange.getRight()))
                .collect(Collectors.toSet());
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(of(15, 18), of(2, 6), of(8, 10), of(1, 3), of(5, 9))},
                {of(of(15, 18), of(2, 6), of(8, 10), of(1, 3), of(5, 9), of(0, 100))}
        };
    }

    @Test(dataProvider = "testData")
    public void testWithTree(List<List<Integer>> intervals) {
        RangeSet<Integer> r = TreeRangeSet.create();
        for (List<Integer> interval : intervals) {
            r.add(closed(interval.get(0), interval.get(1)));
        }

        assertEquals(getOverlappedIntervalsWithTree(intervals), r.asRanges());
        assertEquals(getOverlappedIntervalsWithList(intervals), r.asRanges());
    }

    /*
    Aka eat next interval - add only if there is a gap
    NlogN
     */
    private Set<Range<Integer>> getOverlappedIntervalsWithList(List<List<Integer>> intervals) {
        Set<Range<Integer>> set = new HashSet<>();

        // sort by left border
        List<IntRange> sortedRanges = intervals.stream()
                .map(interval -> new IntRange(interval.get(0), interval.get(1)))
                .sorted((o1, o2) -> {
                    if (o1.getLeft() != o2.getLeft()) {
                        return o1.getLeft() - o2.getLeft();
                    } else {
                        return o1.getRight() - o2.getRight();
                    }
                })
                .collect(Collectors.toList());

        IntRange prev = sortedRanges.get(0);
        IntRange curr;
        for (int i = 0; i < sortedRanges.size(); i++) {
            curr = sortedRanges.get(i);
            if (curr.getLeft() > prev.getRight()) {
                set.add(closed(prev.left, prev.right));
                prev = curr;
            } else {
                prev = new IntRange(prev.getLeft(), Math.max(curr.getRight(), prev.getRight()));
            }
        }

        set.add(closed(prev.left, prev.right));

        return set;
    }

    @Test(dataProvider = "testData")
    public void testWithLists(List<List<Integer>> intervals) {
        RangeSet<Integer> r = TreeRangeSet.create();
        for (List<Integer> interval : intervals) {
            r.add(closed(interval.get(0), interval.get(1)));
        }

        assertEquals(getOverlappedIntervalsWithList(intervals), r.asRanges());
    }

    //todo: actually we can use one left tree
    private final static class IntRangeSet {
        private TreeMap<Integer, IntRange> leftTree = new TreeMap<>();
        private TreeMap<Integer, IntRange> rightTree = new TreeMap<>();
        private Set<IntRange> ranges = new TreeSet<>();

        public void add(IntRange intRange) {
            int left = intRange.getLeft();
            int right = intRange.getRight();

            Integer newLeftBorder = leftTree.floorKey(left);
            if (newLeftBorder == null || leftTree.get(newLeftBorder).getRight() < left) {
                newLeftBorder = left;
            }

            Integer newRightBorder = rightTree.ceilingKey(right);
            if (newRightBorder == null || rightTree.get(newRightBorder).getLeft() > right) {
                newRightBorder = right;
            }

            IntRange newIntRange = new IntRange(newLeftBorder, newRightBorder);
            leftTree.put(newLeftBorder, newIntRange);
            rightTree.put(newRightBorder, newIntRange);

            remove(leftTree, newLeftBorder, newRightBorder);
            remove(rightTree, newLeftBorder, newRightBorder);

            ranges.add(new IntRange(newLeftBorder, newRightBorder));
        }

        private void remove(TreeMap<Integer, IntRange> tree, Integer from, Integer to) {
            Iterator<Integer> it = tree.keySet().iterator();
            Integer next = -1;
            while (it.hasNext() && next < to) {
                if (next > from) {
                    IntRange intRange = tree.get(next);
                    ranges.remove(new IntRange(intRange.getLeft(), intRange.getRight()));
                    it.remove();
                }
                next = it.next();
            }
        }

        public Set<IntRange> getRanges() {
            return ranges;
        }
    }

    private final static class IntRange implements Comparable<IntRange> {
        private final int left;
        private final int right;

        public IntRange(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntRange intRange = (IntRange) o;
            return left == intRange.left &&
                    right == intRange.right;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(left, right);
        }


        @Override
        public int compareTo(IntRange that) {
            if (this.left < that.left) {
                return -1;
            } else if (this.left > that.left) {
                return 1;
            }

            if (this.right < that.right) {
                return -1;
            } else if (this.right > that.right) {
                return 1;
            }
            return 0;
        }
    }
}

