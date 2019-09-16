package puzzler.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 */
public class FindMinimumInRotatedSortedArray {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(2, 3, 4, 5, 1), 1},
                {intA(3, 4, 5, 1, 2), 1},
                {intA(4, 5, 6, 7, 0, 1, 2), 0},
                {intA(0), 0},
                {intA(0, 1, 2), 0},
                {intA(2, 0, 1), 0},
                {intA(1, 2, 0), 0},
                {intA(0, 1, 2, 3), 0},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int res) {
        assertEquals(findMin(nums), res);
    }

    /**
     * Log(N) binary search attempt to find half where value are lower
     * PASSED
     */
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] minValue = new int[1];
        minValue[0] = Integer.MAX_VALUE;
        minValueBinarySearch(0, nums.length - 1, nums, minValue);

        return minValue[0];
    }

    private void minValueBinarySearch(int left, int right, int[] nums, int[] minValue) {
        if (left >= right) {
            if (left == right) {
                minValue[0] = Math.min(minValue[0], nums[left]);
            }
            return;
        }

        int half = left + (right - left) / 2;

        if (half + 1 == nums.length) return;

        if (Math.min(nums[half], nums[left]) < Math.min(nums[half + 1], nums[right])) {
            minValue[0] = Math.min(minValue[0], nums[half]);
            minValueBinarySearch(left, half, nums, minValue);
        } else {
            minValue[0] = Math.min(minValue[0], nums[half]);
            minValueBinarySearch(half + 1, right, nums, minValue);
        }
    }
}
