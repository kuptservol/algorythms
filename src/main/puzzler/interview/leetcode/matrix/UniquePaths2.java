package puzzler.interview.leetcode.matrix;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * Follow up for "Unique Paths":
 * <p>
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 * <p>
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid. For example, there is one obstacle in the middle of a 3x3 grid as illustrated below,
 * <p>
 * [
 * [0,0,0],
 * [0,1,0],
 * [0,0,0]
 * ]
 * the total number of unique paths is 2.
 */
public class UniquePaths2 {

    int[][] a = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    };

    int[][] b = {
            {1}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {a, 2},
                {b, 0}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] desk, int correctResult) {
        assertEquals(uniquePathsWithObstacles(desk), correctResult);
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j] == 1) {
                    obstacleGrid[i][j] = -1;
                } else {

                }
            }
        }

        for (int i = 0; i < obstacleGrid.length; i++) {
            int val = obstacleGrid[i][0];
            if (val == -1) {
                break;
            }

            obstacleGrid[i][0] = 1;
        }

        for (int i = 0; i < obstacleGrid[0].length; i++) {
            int val = obstacleGrid[0][i];
            if (val == -1) {
                break;
            }

            obstacleGrid[0][i] = 1;
        }

        for (int i = 1; i < obstacleGrid.length; i++) {
            for (int j = 1; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j] == -1)
                    obstacleGrid[i][j] = 0;
                else
                    obstacleGrid[i][j] =
                            (obstacleGrid[i - 1][j] == -1 ? 0 : obstacleGrid[i - 1][j]) +
                                    (obstacleGrid[i][j - 1] == -1 ? 0 : obstacleGrid[i][j - 1]);
            }
        }

        int result = obstacleGrid[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
        return result == -1 ? 0 : result;
    }
}
