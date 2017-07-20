package puzzler.interview.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 23/10/2016
 * Given an array of n positive integers and a positive integer s,
 * find the minimal length of a subarray of which the sum ≥ s. If there isn't one, return 0 instead.
 * <p>
 * For example, given the array [2,3,1,2,4,3] and s = 7, the subarray [4,3]
 * has the minimal length of 2 under the problem constraint.
 */
public class MinimumSizeSubArraySum {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {new int[]{1, 1, 4, 1, 3}, 7, 3},
                {new int[]{2, 3, 1, 2, 4, 3}, 7, 2},
                {new int[]{1, 1, 1, 1, 1, 1}, 7, 0},
                {new int[]{1, 1, 1, 1, 1, 1, 1}, 7, 7},
                {new int[]{1, 1, 8, 1, 1, 1, 1}, 7, 1},
                {new int[]{1, 1, 4, 1, 3, 4}, 7, 2},
                {new int[]{2, 3, 1, 1, 8, 3}, 7, 1}
        };
    }

    /**
     * O(N^2)?
     */
    private int slidingWindow(int[] a, int s) {
        int minL = a.length + 1;
        if (a.length == 0) {
            return minL;
        }

        int left = 0;
        int right = 0;
        int windowSum = a[0];
        int windowL = 1;

        while (left <= right && right < a.length) {
            if (windowSum < s) {
                right++;
                windowL++;
                if (right < a.length) {
                    windowSum += a[right];
                }
            } else {
                minL = Math.min(minL, windowL);
                windowL--;
                windowSum -= a[left];
                left++;
            }
        }

        return minL == a.length + 1 ? 0 : minL;
    }

    @Test(dataProvider = "testData")
    public void test(int[] a, int s, int right) {
        assertEquals(slidingWindow(a, s), right);
    }
}
