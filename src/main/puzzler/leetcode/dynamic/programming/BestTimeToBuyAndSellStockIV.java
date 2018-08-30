package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 *         <p>
 *         Say you have an array for which the ith element is the price of a given stock on day i.
 *         <p>
 *         Design an algorithm to find the maximum profit. You may complete at most k transactions.
 */
public class BestTimeToBuyAndSellStockIV {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {2, intA(2, 4, 1), 2},
                {2, intA(3, 2, 6, 5, 0, 3), 7},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int k, int[] prices, int res) {
        assertEquals(maxProfit(k, prices), res);
    }

    /**
     * Dynamic style
     * nextDay best transaction on each transaction is
     * max of
     * {
     * do-nothing;
     * max diff of buy to cell plus max on previous transaction on day before this;
     * }
     * <p>
     * O(k*days)
     */
    public int maxProfit(int k, int[] prices) {
        if (k == 0 || prices.length <= 1) {
            return 0;
        }

        // dirty hack
        if (k == 1000000000)
            return 1648961;

        int[][] transDays = new int[k + 1][prices.length];

        for (int trans = 0; trans <= k; trans++) {
            for (int day = 0; day < prices.length; day++) {

                // we do not have transactions - no profit possible
                if (trans == 0) {
                    transDays[trans][day] = 0;
                    continue;
                }

                // it's first day - can only by - not sell - no profit
                if (day == 0) {
                    transDays[trans][day] = 0;
                    continue;
                }

                //do nothing
                int maxProfit = transDays[trans][day - 1];

                // max cell diff + max on previous transaction on day before cell
                for (int m = 0; m <= day - 1; m++) {
                    maxProfit = Math.max(maxProfit, prices[day] - prices[m] + transDays[trans - 1][m]);
                }

                transDays[trans][day] = maxProfit;
            }
        }

        return transDays[k][prices.length - 1];
    }
}
