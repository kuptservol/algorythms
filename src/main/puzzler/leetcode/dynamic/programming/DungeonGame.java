package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class DungeonGame {

    int[][] first = {
            {-2, -3, 3},
            {-5, -10, 1},
            {10, 30, -5}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {first, 7}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] dungeon, int res) {
        assertEquals(calculateMinimumHP(dungeon), res);
    }

    /**
     * DP solution
     * The key thing to realize - that we must find minimum sum path from finish to start
     * But on every cell cell we cannot have minus value - so before cell we must have 1 or more
     */
    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon.length == 0 || dungeon[0].length == 0) {
            return 0;
        }

        // m x n
        int m = dungeon.length;
        int n = dungeon[0].length;

        // bottom-right cell
        dungeon[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][n - 1], 1);

        //bottom row
        for (int i = n - 2; i >= 0; i--) {
            dungeon[m - 1][i] = Math.max(dungeon[m - 1][i + 1] - dungeon[m - 1][i], 1);
        }

        //right column
        for (int i = m - 2; i >= 0; i--) {
            dungeon[i][n - 1] = Math.max(dungeon[i + 1][n - 1] - dungeon[i][n - 1], 1);
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dungeon[i][j] = Math.min(
                        Math.max(dungeon[i + 1][j] - dungeon[i][j], 1),
                        Math.max(dungeon[i][j + 1] - dungeon[i][j], 1)
                );
            }
        }

//        printMatrix(dungeon);

        return dungeon[0][0];
    }
}
