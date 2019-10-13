package puzzler.leetcode.dynamic.programming;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * You are given coins of different denominations and a total amount of money amount.
 * Write a function to compute the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 */
public class CoinChange {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(2, 4), 4, 1},
                {intA(4, 3), 10, 3},
                {intA(1, 2, 5), 11, 3},
                {intA(2), 3, -1},
                {intA(3), 3, 1},
                {intA(3, 2, 1), 6, 2},
                {intA(3, 4), 5, -1},
                {intA(5, 2), 4, 2},
                {intA(3, 2), 4, 2},
                {intA(3, 2), 4, 2},
                {intA(186, 419, 83, 408), 6249, 20},
                {intA(1, 2147483647), 2, 2},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] coins, int amount, int c) {
        //assertEquals(coinChangeDynamicOnArray(coins, amount), c);
        //assertEquals(coinChangeDFS(coins, amount), c);
        assertEquals(coinChangeDynamicOnMap(coins, amount), c);
    }

    /**
     * simple dfs search
     * Do not pass - time consuming
     * O(amount^coins.length)
     */
    private int coinChangeDFS(int[] coins, int amount) {
        return coinChangeDFS_(coins, amount, 0);
    }

    private int coinChangeDFS_(int[] coins, int amount, int num) {
        if (amount < 0) {
            return -1;
        }

        if (amount == 0) {
            return num;
        }

        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int minNum = coinChangeDFS_(coins, amount - coin, num + 1);
            if (minNum != -1) {
                min = Math.min(minNum, min);
            }
        }

        if (min == Integer.MAX_VALUE) {
            return -1;
        } else {
            return min;
        }
    }

    /**
     * num pos - is number we can reach and value of num is number of hopes to reach - we get minimum of it
     * do not passed - cause amount + 1 where amount = MAX_INT is wrong array size
     * maybe map can be less mem consuming here
     */
    public int coinChangeDynamicOnArray(int[] coins, int amount) {
        int[] num = new int[amount + 1];

        // mark start
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] < num.length) {
                // set first hop
                num[coins[i]] = 1;
            }
        }

        for (int i = 0; i < num.length; i++) {
            int hops = num[i];

            if (hops != 0) {
                for (int j = 0; j < coins.length; j++) {
                    if (coins[j] + i < num.length) {
                        // set first hop
                        num[coins[j] + i] =
                                num[coins[j] + i] == 0
                                        ? hops + 1
                                        : Math.min(hops + 1, num[coins[j] + i]);
                    }
                }
            }
        }

        return num[amount] == 0 ? -1 : num[amount];
    }

    /**
     * Bottom up dynamic soluton
     * nums key - is number we can reach and value of num is number of hopes to reach - we get minimum of it
     * Passed
     * time O(amount*coins.len)
     * space O(amount)
     */
    public int coinChangeDynamicOnMap(int[] coins, int amount) {
        if(amount==0){
            return 0;
        }
        Map<Integer, Integer> nums = new HashMap<>();

        for (int i = 0; i < coins.length; i++) {
            if (coins[i] <= amount) {
                // set first hop
                nums.put(coins[i], 1);
            }
        }

        for (int i = 1; i <= amount; i++) {
            int hops = nums.getOrDefault(i, -1);

            if (hops != -1) {
                for (int j = 0; j < coins.length; j++) {
                    if (coins[j] + i <= amount) {

                        nums.put(coins[j] + i,
                                nums.getOrDefault(coins[j] + i, -1) == -1
                                        ? hops + 1
                                        : Math.min(hops + 1, nums.get(coins[j] + i)));
                    }
                }
            }
        }

        return nums.getOrDefault(amount, -1);
    }
}
