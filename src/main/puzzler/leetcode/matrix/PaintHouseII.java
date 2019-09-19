package puzzler.leetcode.matrix;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 * There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with a certain color
 * is different. You have to paint all the houses such that no two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is represented by a n x k cost matrix. For example, costs[0][0] is the cost of
 * painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint
 * all houses.
 */
public class PaintHouseII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(intA(1, 5, 3), intA(2, 9, 4)), 5},
                {intA(intA(5, 8, 6), intA(19, 14, 13), intA(7, 5, 12), intA(14, 15, 17), intA(3, 20, 10)), 43},
                {intA(intA(17, 2, 17), intA(16, 16, 5), intA(14, 3, 19)), 10},
                {intA(intA(20, 19, 11, 13, 12, 16, 16, 17, 15, 9, 5, 18), intA(3, 8, 15, 17, 19, 8, 18, 3, 11, 6, 7, 12),
                        intA(15, 4, 11, 1, 18, 2, 10, 9, 3, 6, 4, 15)), 9}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] costs, int res) {
        assertEquals(minCostII(costs), res);
    }

    /**
     * Same attempt as in PaintHouse
     * O(N*K^2)
     *
     * PASSES
     */
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0] == null || costs[0].length == 0) {
            return 0;
        }

        for (int i = 1; i < costs.length; i++) {
            for (int j = 0; j < costs[0].length; j++) {
                costs[i][j] = minExcludingI(costs[i - 1], j) + costs[i][j];
            }
        }

        return minExcludingI(costs[costs.length - 1], costs[0].length);
    }

    private int minExcludingI(int[] costs, int excludingI) {
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < costs.length; i++) {
            if (i == excludingI) continue;

            min = Math.min(min, costs[i]);
        }

        return min;
    }
}
