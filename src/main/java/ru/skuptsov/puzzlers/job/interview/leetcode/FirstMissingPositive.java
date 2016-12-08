package ru.skuptsov.puzzlers.job.interview.leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * <p>
 * Given an unsorted integer array, find the first missing positive integer.
 * <p>
 * Your algorithm should run in O(n) time and uses constant space.
 */
public class FirstMissingPositive {

    @DataProvider
    public Object[][] testData() {
        int[] ints1 = {3, 1, 5, 4, 2};
        int[] ints2 = {1, 2, 0};
        int[] ints3 = {3, 4, -1, 1};
        int[] ints4 = {3, 1, 5, -10000, 2};
        return new Object[][]{
                {ints1, 6},
                {ints2, 3},
                {ints3, 2},
                {ints4, 4}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int b) {
        assertEquals(firstMissingPositive(nums), b);
    }

    public int firstMissingPositive(int[] nums) {
        sortToPos(nums, 0);

        if (nums[0] > 1) {
            return 1;
        } else {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != i + 1 || nums[i] > nums.length) {
                    return i + 1;
                }
            }
        }

        return nums[nums.length - 1] + 1;
    }

    private void sortToPos(int[] nums, int pos) {
        int N = nums.length;
        int nextVal = nums[pos];
        if (nextVal > 0 && nextVal <= N) {
            if (nextVal != pos + 1) {
                int nextPos = nextVal - 1;
                int nextPosVal = nums[nextPos];
                nums[pos] = nextPosVal;
                nums[nextPos] = nextVal;

                sortToPos(nums, pos);
            }
        } else {
            sortToPos(nums, ++pos);
        }
    }
}
