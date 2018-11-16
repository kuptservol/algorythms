package puzzler.leetcode.stringarray;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 */
public class MoveZeroes {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(0, 1, 0, 3, 12), intA(1, 3, 12, 0, 0)},
                {intA(1, 1, 1, 3, 12), intA(1, 1, 1, 3, 12)},
                {intA(1, 1, 1, 3, 0), intA(1, 1, 1, 3, 0)},
                {intA(1, 1, 1, 0, 0), intA(1, 1, 1, 0, 0)},
                {intA(0, 1, 1, 0), intA(1, 1, 0, 0)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int[] answer) {
        moveZeroes(nums);
        assertTrue(Arrays.equals(nums, answer));
    }

    public void moveZeroes(int[] nums) {

        int zeros = 0;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                j++;
            } else {
                zeros++;
            }
        }

        for (int i = nums.length - zeros; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
