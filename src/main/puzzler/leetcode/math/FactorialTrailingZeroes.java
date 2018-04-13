package puzzler.leetcode.math;

import com.google.common.math.BigIntegerMath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given an integer n, return the number of trailing zeroes in n!.
 *         <p>
 *         Note: Your solution should be in logarithmic time complexity
 */
public class FactorialTrailingZeroes {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {5, 1},
                {10, 2},
                {14, 2},
                {15, 3},
                {25, 6},
                {35, 8},
                {125, 31},
                {1808548329, 452137076},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int in, int result) {
//        printFactorial(in);
        int actual = trailingZeroes(in);
        System.out.println(actual);
        assertEquals(actual, result);
    }

    public int trailingZeroes(int n) {
        return numOf5(n);
    }

    public int numOf5(int number) {
        int numOf5 = number / 5;
        if (numOf5 == 0) {
            return 0;
        }
        numOf5 += numOf5(numOf5);

        return numOf5;
    }

    public static void printFactorial(int number) {
        System.out.println(BigIntegerMath.factorial(number).toString());
    }
}
