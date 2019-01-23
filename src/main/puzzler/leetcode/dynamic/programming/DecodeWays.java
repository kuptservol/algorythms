package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:
 *
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * Given a non-empty string containing only digits, determine the total number of ways to decode it.
 */
public class DecodeWays {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"12", 2},
                {"1", 1},
                {"0", 0},
                {"90", 0},
                {"900", 0},
                {"101", 1},
                {"20", 1},
                {"02", 0},
                {"91", 1},
                {"226", 3},
                {"220", 1},
                {"2202", 1},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, int res) {
        assertEquals(numDecodings(s), res);
    }

    // dynamic
    // each position in numWays - is how many ways to reach it
    // so next position is current ways + next ways - if it's reachable
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] nums = s.toCharArray();
        int[] numWays = new int[s.length()];

        //start from one step left of array
        int nextNumOneStep = nums[0] - '1' + 1;
        if (nextNumOneStep != 0) {
            numWays[0] = 1;
        } else {
            return 0;
        }

        int nextNumSecondStep;
        Integer next2Val;
        if (nums.length > 1) {
            nextNumSecondStep = nums[1] - '1' + 1;
            next2Val = Integer.valueOf(nextNumOneStep + "" + nextNumSecondStep);
            if (next2Val <= 26) {
                numWays[1] = 1;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (i + 1 <= s.length() - 1) {
                nextNumOneStep = nums[i + 1] - '1' + 1;
                if (nextNumOneStep != 0) {
                    numWays[i + 1] = numWays[i] + numWays[i + 1];
                } else {
                    continue;
                }
            }

            if (i + 2 <= s.length() - 1) {
                nextNumSecondStep = nums[i + 2] - '1' + 1;
                next2Val = Integer.valueOf(nextNumOneStep + "" + nextNumSecondStep);
                if (next2Val <= 26) {
                    numWays[i + 2] = numWays[i] + numWays[i + 2];
                }
            }
        }

        return numWays[s.length() - 1];
    }
}
