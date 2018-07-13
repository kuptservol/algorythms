package puzzler.leetcode.stringarray;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 *         Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.
 *         <p>
 *         Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 */
public class RemoveDuplicatesFromSortedArray {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(1, 1, 2), intA(1, 2)},
                {intA(1, 1, 1), intA(1)},
                {intA(2, 1, 1, 1), intA(2, 1)},
                {intA(0, 0, 1, 1, 1, 2, 2, 3, 3, 4), intA(0, 1, 2, 3, 4)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int[] res) {
        int length = removeDuplicates(nums);
        assertEquals(Arrays.copyOf(nums, length), res);
    }

    public int removeDuplicates(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return nums.length;
        }

        int i = 1;

        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[j - 1]) {
                nums[i] = nums[j];
                i++;
            }
        }

        return i;
    }
}
