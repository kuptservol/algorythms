package puzzler.leetcode.heap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertArrayEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 * Assume you have an array of length n initialized with all 0's and are given k update operations.
 *
 * Each operation is represented as a triplet: [startIndex, endIndex, inc] which increments each element of subarray A[startIndex ...
 * endIndex] (startIndex and endIndex inclusive) with inc.
 *
 * Return the modified array after all k operations were executed.
 */
public class RangeAddition {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {
                        intA(intA(1, 3, 2), intA(2, 4, 3), intA(0, 2, -2)),
                        5,
                        intA(-2, 0, 3, 5, 3)
                }
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] updates, int length, int[] result) {
        assertArrayEquals(getModifiedArraySimpleAddition(length, updates), result);
        assertArrayEquals(getModifiedArraySimpleAdditionUpdateOptimization(length, updates), result);
    }

    /**
     * first attempt simply to apply all range additions to array - can be optimal for long ranges in updates
     * O(N*K) N - avg range array length, K - num of ranges
     * actually passes - but i think such solution proposed to bу not acceptable cause time limit exceeded
     */
    public int[] getModifiedArraySimpleAddition(int length, int[][] updates) {
        int[] result = new int[length];

        for (int[] update : updates) {
            for (int i = update[0]; i <= update[1]; i++) {
                result[i] += update[2];
            }
        }

        return result;
    }

    /**
     * Same as above but with in-between range update optimization
     * idea is to mark +range.val on start border and -range.val on end+1 of range
     *
     * then by one pass mark fill result with partial sums
     * complexity is better -> O(N+K) N - array length, K - num of ranges
     */
    public int[] getModifiedArraySimpleAdditionUpdateOptimization(int length, int[][] updates) {
        int[] result = new int[length];

        for (int[] update : updates) {
            int val = update[2];
            result[update[0]] += val;
            if (update[1] + 1 < length) {
                result[update[1] + 1] -= val;
            }
        }

        for (int i = 1; i < length; i++) {
            result[i] += result[i - 1];
        }

        return result;
    }
}
