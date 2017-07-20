package puzzler.interview.leetcode;

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
        int[] ints5 = {};
        int[] ints6 = {2, 2};
        int[] ints7 = {1, 1};
        return new Object[][]{
                {ints1, 6},
                {ints2, 3},
                {ints3, 2},
                {ints4, 4},
                {ints5, 1},
                {ints6, 1},
                {ints7, 2}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int b) {
        assertEquals(firstMissingPositive(nums), b);
    }

    public int firstMissingPositive(int[] nums) {
        if (nums.length == 0) {
            return 1;
        }

        //sortToPosRecursive(nums, 0);

        sortToPosCycle(nums);

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

    private void sortToPosCycle(int[] nums) {
        int N = nums.length;
        for (int pos = 0; pos < N; ) {
            int nextVal = nums[pos];
            if (nextVal > 0 && nextVal <= N && nextVal != pos + 1) {
                int nextPos = nextVal - 1;
                int nextPosVal = nums[nextPos];
                if (nextVal == nextPosVal) {
                    pos++;
                    continue;
                }
                nums[pos] = nextPosVal;
                nums[nextPos] = nextVal;
            } else {
                pos++;
            }
        }
    }

    private void sortToPosRecursive(int[] nums, int pos) {
        if (pos > nums.length - 1) return;
        int N = nums.length;
        int nextVal = nums[pos];
        if (nextVal > 0 && nextVal <= N && nextVal != pos + 1) {
            int nextPos = nextVal - 1;
            int nextPosVal = nums[nextPos];

            if (nextVal == nextPosVal) {
                sortToPosRecursive(nums, ++pos);
            }

            nums[pos] = nextPosVal;
            nums[nextPos] = nextVal;

            sortToPosRecursive(nums, pos);
        } else {
            sortToPosRecursive(nums, ++pos);
        }
    }
}
