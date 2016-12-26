package ru.skuptsov.puzzlers.job.interview.leetcode.matrix;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 18/12/2016
 * <p>
 * Given a m x n grid filled with non-negative numbers,
 * find a path from top left to bottom right which minimizes the sum of all numbers along its path.
 * <p>
 * Note: You can only move either down or right at any point in time
 */
public class MinimumPathSum {

    int[][] testGrid1 = {{0}};
    int[][] testGrid2 = {{1, 0, 2}, {3, 0, 5}, {4, 5, 6}};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {testGrid1, 0},
                {testGrid2, 12}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] grid, int n) {
        assertEquals(minPathSum(grid), n);
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i - 1][0];
        }

        for (int i = 1; i < n; i++) {
            grid[0][i] += grid[0][i - 1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] = grid[i][j] + Math.min(grid[i][j - 1], grid[i - 1][j]);
            }
        }

        return grid[m - 1][n - 1];
    }
}

/*
1,0,2
3,0,5
4,5,6

1,1,3
4,1,6
8,6,12

 */
