package puzzler.leetcode.matrix;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 * There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. The cost of painting each
 * house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, costs[0][0] is the cost of
 * painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so on... Find the minimum cost to
 * paint all houses.
 */
public class PaintHouse {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(intA(5, 8, 6), intA(19, 14, 13), intA(7, 5, 12), intA(14, 15, 17), intA(3, 20, 10)), 43},
                {intA(intA(17, 2, 17), intA(16, 16, 5), intA(14, 3, 19)), 10}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] costs, int res) {
        assertEquals(minCostDFS(costs), res);
        assertEquals(minCostSimpleDynamic(costs), res);
    }

    /**
     * Simply find min on each level in-place
     */
    public int minCostSimpleDynamic(int[][] costs) {
        if (costs.length == 0) {
            return 0;
        }

        if (costs[0].length == 0) {
            return 0;
        }

        int l = costs.length - 1;
        for (int i = 1; i <= l; i++) {
            costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
            costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
            costs[i][2] += Math.min(costs[i - 1][1], costs[i - 1][0]);
        }

        return Math.min(costs[l][2], Math.min(costs[l][0], costs[l][1]));
    }

    /**
     * First DFS attempt - just walk all pathes one direction - O(N_houses)
     * Time Limit Exceeded
     */
    public int minCostDFS(int[][] costs) {
        int[] costMin = new int[1];
        costMin[0] = Integer.MAX_VALUE;

        dfs(costs, 0, costMin, 0, -1);

        return costMin[0];
    }

    private void dfs(int[][] costs, int level, int[] costMin, int currentCost, int prevColorChoice) {
        if (level == costs.length) {
            costMin[0] = Math.min(currentCost, costMin[0]);

            return;
        }

        for (int i = 0; i < 3; i++) {
            if (i == prevColorChoice) {
                continue;
            }

            dfs(costs, level + 1, costMin, currentCost + costs[level][i], i);
        }
    }
}
