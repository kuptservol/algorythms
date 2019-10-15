package puzzler.leetcode.map;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * There is a fence with n posts, each post can be painted with one of the k colors.
 *
 * You have to paint all the posts such that no more than two adjacent fence posts have the same color.
 *
 * Return the total number of ways you can paint the fence.
 */
public class NumWays {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {3, 2, 6},
                {3, 5, 120},
                {4, 3, 66},
                {5, 6, 7200}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int n, int k, int res) {
        assertEquals(numWaysDfs(n, k), res);
        assertEquals(numWaysDynamic(n, k), res);
    }

    /**
     * passes
     */
    public int numWaysDynamic(int n, int k) {
        if (n == 0) return 0;
        if (n == 1) return k;

        // possible ways to rich color at ith fence
        int[] ways = new int[n + 1];

        // starting with 1 color - then multiply ways to colors num
        // first color way - is 1
        ways[1] = 1;
        // second can be any
        ways[2] = k;
        for (int fence = 3; fence <= n; fence++) {
            ways[fence] = (k - 1) * ways[fence - 2] + (k - 1) * ways[fence - 1];
        }

        return ways[n] * k;
    }

    /**
     * dfs attempt
     * time limit exceeded
     */
    public int numWaysDfs(int n, int k) {
        Set<String> ways = new HashSet<>();

        if (n == 0) {
            return 0;
        }

        for (int color = 1; color <= k; color++) {
            countWays(ways, color + "", 1, n, k, color, -1);
        }

        return ways.size();
    }

    private void countWays(Set<String> ways, String currColors, int fence, int maxFence, int colorsNum, int prevColor, int prevPrevColor) {
        if (fence >= maxFence) {
            ways.add(currColors);
            return;
        }

        int colorsInARow = -1;
        if (prevPrevColor != -1 && prevColor == prevPrevColor) {
            colorsInARow = prevColor;
        }
        for (int color = 1; color <= colorsNum; color++) {
            if (colorsInARow != color) {
                countWays(ways, currColors + color, fence + 1, maxFence, colorsNum, color, prevColor);
            }
        }
    }
}
