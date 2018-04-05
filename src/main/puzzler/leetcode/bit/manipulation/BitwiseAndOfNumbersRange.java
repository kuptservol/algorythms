package puzzler.leetcode.bit.manipulation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of all numbers in this range, inclusive.
 *         <p>
 *         For example, given the range [5, 7], you should return 4.
 */
public class BitwiseAndOfNumbersRange {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {8, 10, 8},
                {2147483646, 2147483647, 2147483646}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int m, int n, int result) {
        assertEquals(rangeBitwiseAnd(m, n), result);
    }

    public int rangeBitwiseAnd(int m, int n) {

        if (m == n) {
            return n;
        }

        if (n == Integer.MAX_VALUE) {
            n--;
        }

        if (razr(m) != razr(n)) {
            return 0;
        }

        int next = m;
        for (int i = m + 1; i <= n; i++) {
            next = next & i;
        }
        return next;
    }

    public int razr(int x) {
        int razr = 0;
        for (int i = 1; i <= 16; i++) {
            if ((x & 1) == 1) {
                razr = i;
            }
            x = x >> 1;
        }

        return razr;
    }
}

