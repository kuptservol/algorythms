package puzzler.leetcode.map;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * There are n bulbs that are initially off. You first turn on all the bulbs.
 * Then, you turn off every second bulb. On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on). For the ith round, you toggle every i bulb.
 * For the nth round, you only toggle the last bulb. Find how many bulbs are on after n rounds.
 */
public class BulbSwitcher {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {3, 1},
                {1, 1},
                {4, 2},
                {999999, 999}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int n, int result) {
        assertEquals(bulbSwitchTimeExceed(n), result);
        assertEquals(bulbSwitchMath(n), result);
    }

    private int bulbSwitchMath(int n) {
        return (int) Math.sqrt(n);
    }

    public int bulbSwitchTimeExceed(int n) {
        if (n == 0) {
            return 0;
        }

        Map<Integer, Integer> togglesMap = new HashMap<>();

        for (int i = 2; i <= n; i++) {
            for (int j = 1, k = i * j; k <= n; j++, k = i * j) {
                Integer val = togglesMap.getOrDefault(k, 0);
                togglesMap.put(k, ++val);
            }
        }

        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (togglesMap.getOrDefault(i, 0) % 2 == 0) {
                count++;
            }
        }

        return count;
    }
}
