package puzzler.leetcode.matrix;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given an integer matrix, find the length of the longest increasing path.
 *         <p>
 *         From each cell, you can either move to four directions: left, right, up or down.
 *         You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).
 */
public class LongestIncreasingPathInAMatrix {

    int[][] m1 = {
            {9, 9, 4},
            {6, 6, 8},
            {2, 1, 1},
    };

    int[][] m2 = {
            {3, 4, 5},
            {3, 2, 6},
            {2, 2, 1},
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {m1, 4},
                {m2, 4}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] matrix, int res) {
        assertEquals(res, longestIncreasingPath(matrix));
    }

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        return simpleDfsWithSomeKindTopologicalCapturing(matrix);
    }

    /**
     * first naive attempt
     * get all numbers - consider as graph - make dfs from every node
     * with one improvement - caching already followed node paths
     * return the longest topological order length
     *
     * ACCEPTED
     */
    private int simpleDfsWithSomeKindTopologicalCapturing(int[][] matrix) {
        int[][] len = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                len[i][j] = -1;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                dfs(i, j, len, matrix);
            }
        }

        int max = 0;
        for (int i = 0; i < len.length; i++) {
            for (int j = 0; j < len[0].length; j++) {
                max = Math.max(max, len[i][j]);
            }
        }

        return max;
    }

    private int dfs(int i, int j, int[][] len, int[][] matrix) {
        if (len[i][j] != -1) {
            return len[i][j];
        }

        len[i][j] = 0;

        //up
        if (i != 0 && matrix[i - 1][j] > matrix[i][j]) {
            len[i][j] = Math.max(len[i][j], dfs(i - 1, j, len, matrix));
        }

        //down
        if (i != matrix.length - 1 && matrix[i + 1][j] > matrix[i][j]) {
            len[i][j] = Math.max(len[i][j], dfs(i + 1, j, len, matrix));
        }

        //left
        if (j != 0 && matrix[i][j - 1] > matrix[i][j]) {
            len[i][j] = Math.max(len[i][j], dfs(i, j - 1, len, matrix));
        }

        //right
        if (j != matrix[0].length - 1 && matrix[i][j + 1] > matrix[i][j]) {
            len[i][j] = Math.max(len[i][j], dfs(i, j + 1, len, matrix));
        }


        //add current node
        len[i][j] = len[i][j] + 1;
        return len[i][j];
    }
}
