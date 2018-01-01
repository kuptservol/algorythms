package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.primitives.Ints.toArray;
import static org.testng.Assert.assertEquals;

/**
 * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
 */
public class MaximumSubArray {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {toArray(of(-2, 1, -3, 4, -1, 2, 1, -5, 4)), 6},
                {toArray(of(-2)), -2},
                {toArray(of(2)), 2},
                {toArray(of(-1, 100)), 100},
                {toArray(of(-1, -1)), -1},
                {toArray(of(-20, -1, -2)), -1},
                {toArray(of(-2, 1)), 1},
                {toArray(of(-1, -2)), -1},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int answer) {
        assertEquals(maxSubArray(nums), answer);
    }

    public int maxSubArray(int[] nums) {
        return linearSolution(nums);
    }

    private int linearSolution(int[] nums) {
        int maxSubArraySum = nums[0];
        int currSubArraySum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currSubArraySum = Math.max(currSubArraySum + nums[i], nums[i]);
            maxSubArraySum = Math.max(maxSubArraySum, currSubArraySum);
        }

        return Math.max(maxSubArraySum, currSubArraySum);
    }
}
