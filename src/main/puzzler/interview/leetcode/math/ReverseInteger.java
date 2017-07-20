package puzzler.interview.leetcode.math;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 04/12/2016
 * <p>
 * Reverse Integer:
 * <p>
 * Reverse digits of an integer.
 * Example1: x = 123, return 321
 * Example2: x = -123, return -321
 */
public class ReverseInteger {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {123, 321},
                {-123, -321},
                {1230, 321}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int a, int b) {
        assertEquals(reverse(a), b);
    }

    private int reverse(int a) {
        int b = 0;

        while (a != 0) {
            b = 10 * b + a % 10;
            a = a / 10;
        }

        return b;
    }
}
