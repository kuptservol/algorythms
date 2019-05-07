package puzzler.leetcode.math;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * Given two integers dividend and divisor, divide two integers without using multiplication, division and mod operator.
 *
 * Return the quotient after dividing dividend by divisor.
 *
 * The integer division should truncate toward zero.
 */
public class DivideTwoIntegers {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {10, 3, 3},
                {7, -3, -2},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int dividend, int divisor, int result) {
        assertEquals(divide(dividend, divisor), result);
    }

    public int divide(int dividend, int divisor) {
        int result = 0;

        if (divisor == 1) {
            return dividend;
        }

        if (divisor == 0) return Integer.MAX_VALUE;
        if (divisor == -1 && dividend == Integer.MIN_VALUE)
            return Integer.MAX_VALUE;

        int pDividend = Math.abs(dividend);
        int pDivisor = Math.abs(divisor);

        while (pDividend >= pDivisor) {
            //calculate number of left shifts
            int numShift = 0;
            while (pDividend >= (pDivisor << numShift)) {
                numShift++;
            }

            //dividend minus the largest shifted divisor
            result += 1 << (numShift - 1);
            pDividend -= (pDivisor << (numShift - 1));
        }

        if ((dividend < 0 && divisor < 0) || (dividend > 0 && divisor > 0)) {
            return result;
        } else {
            return -result;
        }
    }
}
