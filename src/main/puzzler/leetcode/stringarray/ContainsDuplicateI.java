package puzzler.leetcode.stringarray;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 *         Given an array of integers, find if the array contains any duplicates.
 *         <p>
 *         Your function should return true if any value appears at least twice in the array,
 *         and it should return false if every element is distinct.
 */
public class ContainsDuplicateI {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(1, 2, 3, 1), true},
                {intA(1, 2, 3, 4), false},
                {intA(1, 1, 1, 3, 3, 4, 3, 2, 4, 2), true},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, boolean res) {
        assertEquals(containsDuplicate(nums), res);
    }

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> intsSet = new HashSet<>();
        for (int x : nums) {
            if (intsSet.contains(x)) {
                return true;
            } else {
                intsSet.add(x);
            }
        }

        return false;
    }
}
