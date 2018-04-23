package puzzler.leetcode.matrix;

import java.util.Stack;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class MaximumSquare {

    char[][] m1 = {
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
    };

    char[][] m2 = {
            {'0'}
    };

    char[][] m3 = {
            {'1'}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {m1, 4},
                {m2, 0},
                {m3, 1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(char[][] matrix, int answer) {
        assertEquals(maximalSquare(matrix), answer);
    }

    public int maximalSquare(char[][] matrix) {
        int maxSquare = 0;

        int height = matrix.length;
        if (height == 0) {
            return 0;
        }

        int width = matrix[0].length;

        int[] rowHeights = new int[width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] == '0') {
                    rowHeights[j] = 0;
                } else {
                    rowHeights[j]++;
                }
            }

            maxSquare = Math.max(maxSquare, findMaxSquareHisto(rowHeights));
        }

        return maxSquare;
    }

    private int findMaxSquareHisto(int[] rowHeights) {
        Stack<Integer> heights = new Stack<>();
        int maxSquare = 0;

        int currHeightIndex = 0;

        while (currHeightIndex < rowHeights.length) {
            int currHeight = rowHeights[currHeightIndex];
            if (heights.isEmpty() || currHeight > rowHeights[heights.peek()]) {
                heights.push(currHeightIndex++);
            } else {
                int lastHeightIndex = heights.pop();
                int lastHeight = rowHeights[lastHeightIndex];
                int currWidth = currHeightIndex - (heights.empty() ? 0 : heights.peek() + 1);

                int squareLen = Math.min(currWidth, lastHeight);
                maxSquare = Math.max(squareLen * squareLen, maxSquare);
            }
        }

        while (!heights.empty()) {
            int lastHeightIndex = heights.pop();
            int lastHeight = rowHeights[lastHeightIndex];
            int currWidth = currHeightIndex - (heights.empty() ? 0 : heights.peek() + 1);

            int squareLen = Math.min(currWidth, lastHeight);
            maxSquare = Math.max(squareLen * squareLen, maxSquare);
        }

        return maxSquare;
    }
}
