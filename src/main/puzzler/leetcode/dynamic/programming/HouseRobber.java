package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 *         You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed,
 *         the only constraint stopping you from robbing each of them is that adjacent houses have security system connected
 *         and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *         <p>
 *         Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount
 *         of money you can rob tonight without alerting the police.
 */
public class HouseRobber {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(1, 2, 3, 1), 4},
                {intA(2, 7, 9, 3, 1), 12},
                {intA(1), 1},
                {intA(2, 1), 2},
                {intA(1, 0, 0, 9), 10},
                {intA(1, 0, 0, 9, 10), 11},
                {intA(1, 9, 10), 11},
                {intA(1, 9, 10, 11), 20},
                {intA(1, 9, 10, 0, 11), 22},
                {intA(1, 0, 2, 9), 10},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int res) {
        assertEquals(rob(nums), res);
    }

    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        for (int i = 2; i < nums.length; i++) {
            nums[i] = nums[i] + nums[i - 2];
            nums[i - 1] = Math.max(nums[i - 1], nums[i - 2]);
        }

        return Math.max(nums[nums.length - 1], nums[nums.length - 2]);
    }
}
