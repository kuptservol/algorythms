package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Find the contiguous subarray within an array
 * (containing at least one number) which has the largest product.
 * <p>
 * For example, given the array [2,3,-2,4], the contiguous subarray [2,3] has the largest product = 6.
 *
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class MaximumProductSubArray {

    int[] a1 = {2, 3, -2, 4};
    int[] a2 = {2, 3, -2, -1};
    int[] a3 = {2, 3, 0, 1000};
    int[] a4 = {-2};
    int[] a5 = {-2, 0, -1};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
//                {a1, 6},
                {a2, 12},
//                {a3, 1000},
//                {a4, -2},
//                {a5, 0},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int answer) {
        assertEquals(answer, maxProduct(nums));
    }

    public int maxProduct(int[] nums) {
        int[] minValues = new int[nums.length];
        int[] maxValues = new int[nums.length];
        int result = nums[0];
        minValues[0] = maxValues[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > 0) {
                maxValues[i] = Math.max(nums[i], maxValues[i - 1] * nums[i]);
                minValues[i] = Math.min(nums[i], minValues[i - 1] * nums[i]);
            } else {
                maxValues[i] = Math.max(nums[i], minValues[i - 1] * nums[i]);
                minValues[i] = Math.min(nums[i], maxValues[i - 1] * nums[i]);
            }

            result = Math.max(result, maxValues[i]);
        }

        return result;
    }
}
