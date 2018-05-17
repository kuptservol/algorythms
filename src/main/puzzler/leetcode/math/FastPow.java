package puzzler.leetcode.math;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Implement pow(x, n), which calculates x raised to the power n (xn).
 */
public class FastPow {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {1, 2},
                {0, 1},
                {1, 0},
                {2, 13},
                {3, 13},
                {2, -2},
                {2, 5}
        };
    }

    @Test(dataProvider = "testData")
    public void test(double a, int b) {
        assertEquals(myPow(a, b), Math.pow(a, b));
    }

    public double myPow(double x, int n) {
        long N = n;

        if (n < 0) {
            N = -n;
            x = 1 / x;
        }

//        return fastPowRecursive(x, N);
//        return fastPowIterative(x, N);
        return fastPowIterative2(x, N);
    }

    // O(logN).complx + O(logN).space
    private double fastPowRecursive(double x, long n) {
        if (n == 0) {
            return 1;
        }

        double halfPow = fastPowRecursive(x, n / 2);
        if (n % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return x * halfPow * halfPow;
        }
    }

    // O(logN).complx + O(1).space
    private double fastPowIterative(double x, long n) {
        double result = 1;
        double currPow = x;

        for (long i = n; i > 0; i /= 2) {
            if (i % 2 == 1) {
                result = result * currPow;
            }

            currPow = currPow * currPow;
        }

        return result;
    }

    // O(logN).complx + O(1).space
    private double fastPowIterative2(double x, long n) {
        double result = 1;
        double currPow = x;

        while (n > 0) {
            if ((n & 1) == 1) {
                result *= currPow;
            }

            currPow *= currPow;
            n >>= 1;
        }

        return result;
    }
}
