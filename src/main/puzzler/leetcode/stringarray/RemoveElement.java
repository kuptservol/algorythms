package puzzler.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 */
public class RemoveElement {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(3, 2, 2, 3), 3, 2},
                {intA(0, 1, 2, 2, 3, 0, 4, 2), 2, 5},
                {intA(2, 2, 2), 2, 0},
                {intA(2, 3, 2, 3), 3, 2},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int val, int res) {
        assertEquals(removeElement(nums, val), res);
    }

    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }

        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }

        return i;
    }
}
