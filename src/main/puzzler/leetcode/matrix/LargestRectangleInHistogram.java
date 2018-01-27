package puzzler.leetcode.matrix;

import java.util.Stack;

import com.google.common.primitives.Ints;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1,
 * find the area of largest rectangle in the histogram.
 */
public class LargestRectangleInHistogram {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {Ints.toArray(of(2, 1, 5, 6, 2, 3)), 10},
                {Ints.toArray(of(6, 2, 5, 4, 5, 1, 6)), 12},
                {Ints.toArray(of(4, 4, 4)), 12},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] heights, int result) {
        assertEquals(largestRectangleArea(heights), result);
    }

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
