package puzzler.leetcode.stringarray;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 *         Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice and return the new length.
 *         Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 */
public class RemoveDuplicatesFromSortedArrayII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(1, 1, 1, 2, 2, 3), intA(1, 1, 2, 2, 3)},
                {intA(1, 1, 2, 2, 2, 3, 3), intA(1, 1, 2, 2, 3, 3)},
                {intA(1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3), intA(1, 1, 2, 2, 3, 3)},
                {intA(1, 2, 3, 3, 3, 4), intA(1, 2, 3, 3, 4)},
                {intA(2, 3, 3, 2, 3, 2), intA(2, 3, 3, 2, 3, 2)},
                {intA(0, 0, 1, 1, 1, 1, 2, 3, 3), intA(0, 0, 1, 1, 2, 3, 3)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int[] res) {
        int length = removeDuplicates(nums);
        assertEquals(Arrays.copyOf(nums, length), res);
    }

    public int removeDuplicates(int[] nums) {

        if (nums.length <= 2) {
            return nums.length;
        }

        int prev = 1;
        int curr = 2;

        while (curr < nums.length) {
            if (nums[curr] == nums[prev] && nums[curr] == nums[prev - 1]) {
                curr++;
            } else {
                prev++;
                nums[prev] = nums[curr];
                curr++;
            }
        }

        return prev + 1;
    }
}
