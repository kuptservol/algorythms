package puzzler.leetcode.stringarray;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.
 *         <p>
 *         For example, given the following triangle
 *         [
 *         [2],
 *         [3,4],
 *         [6,5,7],
 *         [4,1,8,3]
 *         ]
 *         The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 *         <p>
 *         Note: Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.
 */
public class Triangle {

    @DataProvider
    public Object[][] testData() {

        return new Object[][]{
                {of(of(2), of(3, 4), of(6, 5, 7), of(4, 1, 8, 3)), 11},
                {of(of(-1), of(2, 3), of(1, -1, -3)), -1},
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<List<Integer>> triangle, int res) {
        assertEquals(minimumTotal(triangle), res);
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int[] total = new int[triangle.size()];
        int l = triangle.size() - 1;

        for (int i = 0; i < triangle.get(l).size(); i++) {
            total[i] = triangle.get(l).get(i);
        }

        // iterate from last second row
        for (int i = triangle.size() - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i + 1).size() - 1; j++) {
                total[j] = triangle.get(i).get(j) + Math.min(total[j], total[j + 1]);
            }
        }

        return total[0];
    }
}
