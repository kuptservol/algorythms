package puzzler.interview.leetcode.stringarray;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.primitives.Ints.toArray;
import static org.testng.Assert.assertEquals;

/**
 * Created by Сергей on 14.12.2016.
 * <p>
 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
 * For example, given [100, 4, 200, 1, 3, 2], the longest consecutive elements sequence should be [1, 2, 3, 4]. Its length is 4.
 * <p>
 * Your algorithm should run in O(n) complexity.
 */
public class LongestConsecutiveSequence {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {toArray(of(100, 4, 200, 1, 3, 2)), 4},
                {toArray(of(0, -1)), 2},
                {toArray(of(2147483646, -2147483647, 0, 2, 2147483644, -2147483645, 2147483645)), 3},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] a, int b) {
        assertEquals(longestConsecutive(a), b);
    }

    public int longestConsecutive(int[] nums) {
        return getLongestConsecutiveSequenceLengthSetIndex(nums);
    }

    /**
     * Memory O(N)
     */
    private int getLongestConsecutiveSequenceLengthSetIndex(int[] nums) {
        if (nums.length == 1) return 1;
        if (nums.length == 0) return 0;

        Set<Integer> values = new HashSet<>(1000000);
        for (int num : nums) {
            values.add(num);
        }

        int longestConsecutiveSequenceLength = 0;
        for (int num : nums) {
            int currLongestConsecutiveSequenceLength = 1;
            int left = num - 1;
            int right = num + 1;

            while (values.contains(left)) {
                values.remove(left);
                currLongestConsecutiveSequenceLength++;
                left -= 1;
            }

            while (values.contains(right)) {
                values.remove(right);
                currLongestConsecutiveSequenceLength++;
                right += 1;
            }

            longestConsecutiveSequenceLength = Math.max(currLongestConsecutiveSequenceLength, longestConsecutiveSequenceLength);
        }

        return longestConsecutiveSequenceLength;
    }
}
