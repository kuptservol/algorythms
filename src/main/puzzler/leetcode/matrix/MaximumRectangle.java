package puzzler.leetcode.matrix;

import java.util.Stack;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
 */

/**
 * Solution is to convert this 2-D problem to slices of 1-D and solve it in by LargestRectangleInHistogram problem
 */
public class MaximumRectangle {

    char[][] m1 = {
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
    };

    char[][] m2 = {
            {'1', '1', '1', '1', '1'}
    };

    char[][] m3 = {
            {'0'},
            {'1'}
    };

    char[][] m4 = {
            {'1'},
            {'0'}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {m1, 6},
                {m2, 5},
                {m3, 1},
                {m4, 1},
        };
    }

    @Test(dataProvider = "testData")
    public void test(char[][] matrix, int answer) {
        assertEquals(maximalRectangle(matrix), answer);
    }

    public int maximalRectangle(char[][] matrix) {
        int maxArea = 0;
        int rowsCount = matrix.length;
        if (rowsCount == 0) {
            return maxArea;
        }

        int columnsCount = matrix[0].length;

        int[] heights = new int[columnsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (matrix[i][j] == '0') {
                    heights[j] = 0;
                } else {
                    heights[j]++;
                }
            }

            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    // code from LargestRectangleInHistogram problem
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> minHeights = new Stack<>();
        int maxArea = 0;
        int nextMaxArray;

        int i = 0;
        while (i < heights.length) {
            int nextHeight = heights[i];
            if (minHeights.empty() || nextHeight > heights[minHeights.peek()]) {
                minHeights.push(i++);
            } else {
                // we can recount current bars back in stack - cause sequence of bars > current height cannot be longer - cause found lesser
                int lastIndex = minHeights.pop();
                int lastHeight = heights[lastIndex];
                int indexDiff = i - (minHeights.empty() ? 0 : minHeights.peek() + 1);
                nextMaxArray = lastHeight * indexDiff;
                maxArea = Math.max(maxArea, nextMaxArray);
            }
        }

        //do for the rest in stack
        while (!minHeights.empty()) {
            int lastIndex = minHeights.pop();
            int lastHeight = heights[lastIndex];
            int indexDiff = i - (minHeights.empty() ? 0 : minHeights.peek() + 1);
            nextMaxArray = lastHeight * indexDiff;
            maxArea = Math.max(maxArea, nextMaxArray);
        }

        return maxArea;
    }
}
