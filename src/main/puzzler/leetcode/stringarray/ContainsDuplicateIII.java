package puzzler.leetcode.stringarray;

import java.util.TreeSet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 *         Given an array of integers, find out whether there are two distinct
 *         indices i and j in the array such that the absolute difference between nums[i] and nums[j] is at most t
 *         and the absolute difference between i and j is at most k.
 *         <p>
 *         Example 1:
 *         <p>
 *         Input: [1,2,3,1], k = 4, t = 0
 *         Output: true
 *         Example 2:
 *         <p>
 *         Input: [1,0,1,1], k = 1, t = 0
 *         Output: true
 *         Example 3:
 *         <p>
 *         Input: [4,2], k = 2, t = 1
 *         Output: false
 */
public class ContainsDuplicateIII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(1, 2, 3, 1), true, 4, 0},
                {intA(1, 0, 1, 1), true, 1, 0},
                {intA(4, 2), false, 2, 1},
                {intA(1, 3, 6, 2), true, 1, 2},
                {intA(-1, 2147483647), false, 1, 2147483647}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, boolean res, int k, int t) {
        assertEquals(containsNearbyAlmostDuplicate(nums, k, t), res);
    }

    // sorted sliding window size of max = k with bst
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums.length == 0) {
            return false;
        }

        TreeSet<Long> window = new TreeSet<>();

        for (int i = 0; i < nums.length; i++) {
            int next = nums[i];

            Long g = window.ceiling((long) next);
            if (g != null && g - next <= t) return true;

            Long s = window.floor((long) next);
            if (s != null && next - s <= t) return true;

            window.add((long) next);
            if (window.size() > k) {
                window.remove((long) nums[i - k]);
            }
        }

        return false;
    }
}
