package puzzler.leetcode.matrix;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its
 *         upper left corner (row1, col1) and lower right corner (row2, col2).
 */
public class RangeSumQuery {

    int[][] matrix = {
            {3, 0, 1, 4, 2},
            {5, 6, 3, 2, 1},
            {1, 2, 0, 1, 5},
            {4, 1, 0, 1, 7},
            {1, 0, 3, 0, 5}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {2, 1, 4, 3, 8},
                {1, 1, 2, 2, 11},
                {1, 2, 2, 4, 12}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int row1, int col1, int row2, int col2, int res) {
        NumMatrixLinearSumCache obj = new NumMatrixLinearSumCache(matrix);
        assertEquals(obj.sumRegion(row1, col1, row2, col2), res);
    }


    /**
     * First attempt is to to have linear row and column caches of cumulative sums
     * With some optimization - we use column or row cache depending on requested square
     * Accepted
     */
    class NumMatrixLinearSumCache {

        private int[][] matrixRowCumulativeSums;
        private int[][] matrixColumnCumulativeSums;

        public NumMatrixLinearSumCache(int[][] matrix) {
            if (matrix.length == 0 || matrix[0].length == 0) {
                return;
            }

            this.matrixRowCumulativeSums = new int[matrix.length][matrix[0].length];
            this.matrixColumnCumulativeSums = new int[matrix.length][matrix[0].length];

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrixRowCumulativeSums[i][j] =
                            j == 0
                                    ? matrix[i][0]
                                    : matrix[i][j] + matrixRowCumulativeSums[i][j - 1];
                }
            }


            for (int i = 0; i < matrix[0].length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    matrixColumnCumulativeSums[j][i] =
                            j == 0
                                    ? matrix[0][i]
                                    : matrix[j][i] + matrixColumnCumulativeSums[j - 1][i];
                }
            }

//            printMatrix(matrixRowCumulativeSums);
//            System.out.println("--------------");
//            printMatrix(matrixColumnCumulativeSums);
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            if (matrixRowCumulativeSums == null) {
                return 0;
            }
            int sum = 0;
            if (col2 - col1 > row2 - row1) {
                //use column cache
                for (int i = col1; i <= col2; i++) {
                    sum += matrixColumnCumulativeSums[row2][i] -
                            (row1 == 0
                                    ? 0
                                    : matrixColumnCumulativeSums[row1 - 1][i]);
                }

            } else {
                //use row cache
                for (int i = row1; i <= row2; i++) {
                    sum += matrixRowCumulativeSums[i][col2] -
                            (col1 == 0
                                    ? 0
                                    : matrixRowCumulativeSums[i][col1 - 1]);
                }

            }
            return sum;
        }
    }
}
