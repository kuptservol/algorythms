package puzzler.leetcode.math;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class CountPrimes {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {10, 4},
                {11, 4},
                {12, 5},
                {13, 5},
                {1, 0},
                {2, 0},
                {3, 1},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int n, int res) {
        assertEquals(countPrimes(n), res);
    }

    /*
     * Kind of simplified Eratosfen
     */
    public int countPrimes(int n) {
        if (n <= 1) {
            return 0;
        }

        boolean[] notPrimes = new boolean[n + 1];

        notPrimes[0] = true;
        notPrimes[1] = true;

        for (int i = 2; i <= n / 2; i++) {
            if (!notPrimes[i]) {
                for (int j = 2, k = i * j; k <= n; j++, k = i * j) {
                    notPrimes[k] = true;
                }
            }
        }

        int primes = 0;
        for (int i = 0; i < n; i++) {
            if (!notPrimes[i]) {
                primes++;
            }
        }

        return primes;
    }
}
