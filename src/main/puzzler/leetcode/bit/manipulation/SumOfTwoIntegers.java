package puzzler.leetcode.bit.manipulation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -
 */
public class SumOfTwoIntegers {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {1, 0},
                {1, 2},
                {100, 10011},
                {500345, 213421},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int a, int b) {
        assertEquals(getSum(a, b), a + b);
    }

    public int getSum(int a, int b) {
        if (b == 0) {
            return a;
        }

        int sumIgnoringCarriers = a ^ b;
        int carrier = (a & b) << 1;

        return getSum(sumIgnoringCarriers, carrier);
    }
}
