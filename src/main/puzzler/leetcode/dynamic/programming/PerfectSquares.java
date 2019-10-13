package puzzler.leetcode.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.gradle.internal.impldep.org.junit.Assert.assertEquals;

/**
 * Given a positive integer n,
 * find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
 *
 * @author Sergey Kuptsov
 */
public class PerfectSquares {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {12, 3},
                {1, 1},
                {3, 3},
                {2, 2},
                {49, 1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int n, int res) {
        assertEquals(numSquares(n), res);
    }

    /**
     * Dynamic approach
     * mem: O(N)
     * speed: O(N*num_of_squares_less_n~N)
     *
     * same as just coin task - where coin is perfect square val less than or equal n
     *
     * passes
     */
    public int numSquares(int n) {

        List<Integer> squares = new ArrayList<>();
        for (int i = 1, j = 1; j <= n; i++, j = i * i) {
            squares.add(j);
        }

        int[] minWithSquareHops = new int[n + 1];
        Arrays.fill(minWithSquareHops, n + 1);

        for (int square : squares) {
            minWithSquareHops[square] = 1;
        }

        for (int num = 1; num <= n; num++) {
            int hopsToNum = minWithSquareHops[num];

            if (hopsToNum == n + 1)
                continue;

            for (int square : squares) {
                if (num + square <= n)
                    minWithSquareHops[num + square] = Math.min(hopsToNum + 1, minWithSquareHops[num + square]);
            }
        }

        return minWithSquareHops[n];
    }
}
