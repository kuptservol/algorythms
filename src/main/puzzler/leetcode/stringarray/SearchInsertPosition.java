package puzzler.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.primitives.Ints.toArray;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 10/02/2017
 * <p>
 * Given a sorted array and a target value, return the index if the target is found.
 * If not, return the index where it would be if it were inserted in order.
 * You may assume no duplicates in the array.
 * <p>
 * Here are few examples.
 * <p>
 * [1,3,5,6], 5 -> 2
 * [1,3,5,6], 2 -> 1
 * [1,3,5,6], 7 -> 4
 * [1,3,5,6], 0 -> 0
 */
public class SearchInsertPosition {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {toArray(of(1, 3, 5, 6)), 5, 2},
                {toArray(of(1, 3, 5, 6)), 2, 1},
                {toArray(of(1, 3, 5, 6)), 7, 4},
                {toArray(of(1, 3, 5, 6)), 0, 0},
                {toArray(of()), 0, 0},
                {toArray(of(1, 3)), 3, 1},
                {toArray(of(1)), 2, 1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int target, int pos) {
        assertEquals(searchInsert(nums, target), pos);
    }

    public int searchInsert(int[] nums, int target) {
        if (nums.length == 0 || target <= nums[0]) {
            return 0;
        } else if (target > nums[nums.length - 1]) {
            return nums.length;
        }

        return binaryPosSearch(nums, target);
    }

    private int binaryPosSearch(int[] nums, int target) {
        int hi = nums.length - 1;
        int lo = 0;
        int mid;

        while (hi >= lo) {
            mid = (hi + lo) / 2;

            if (target > nums[mid]) {
                lo = mid + 1;
            } else if (target < nums[mid]) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }

        return nums[hi] > target ? hi - 1 : hi + 1;
    }
}
