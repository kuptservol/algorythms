package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 * Given an unsorted array of integers, find the length of longest increasing subsequence.
 */
public class LongestIncreasingSubsequence {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(1, 2), 2},
                {intA(4, 10, 3, 8), 2},
                {intA(4, 3, 8, 9), 3},
                {intA(7, 9, 1, 2, 3, 10, 11), 5},
                {intA(7, 9, 1, 2, 3), 3},
                {intA(1, 2, 1, 2, 5, 3), 3},
                {intA(1, 2, 5, 3, 5, 3), 4},
                {intA(1, 2, 5, 3, 3, 5), 4},
                {intA(1, 2, 1, 2, 1, 2, 5, 3), 3},
                {intA(10, 9, 2, 5, 3, 7, 101, 18), 4},
                {intA(1), 1},
                {intA(1, -1), 1},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int res) {
        assertEquals(lengthOfLIS(nums), res);
    }

    /**
     * trying to count and cache increasing seq for every node
     * - looking back finding less than current with max increasing seq value
     * O(N^2)
     * mb we can store nearest increasingCountsToTheLeft in tree - so O(NlogN)
     *
     * PASSES
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        int max = 1;

        // counts of increasing sequence left to this pos
        int[] increasingCountsToTheLeft = new int[length];
        increasingCountsToTheLeft[0] = 1;

        for (int i = 1; i < length; i++) {
            int curr = nums[i];

            // find mb smaller with higher value among all left
            int currMax = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < curr) {
                    currMax = Math.max(currMax, increasingCountsToTheLeft[j]);
                }
            }

            increasingCountsToTheLeft[i] = 1 + currMax;

            max = Math.max(increasingCountsToTheLeft[i], max);
        }

        return max;
    }
}
