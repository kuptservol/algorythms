package puzzler.leetcode.bit.manipulation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 19/01/2017
 * <p>
 * Write a function that takes an unsigned integer and returns the number of ’1' bits it has (also known as the Hamming weight).
 * <p>
 * For example, the 32-bit integer ’11' has binary representation 00000000000000000000000000001011,
 * so the function should return 3.
 */
public class HammingWeight {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {11, 3}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int a, int b) {
        assertEquals(hammingWeight(a), b);
    }

    private int hammingWeight(int i) {
        int count = 0;
        for (int j = 0; j < 32; j++) {
            if (((i >>>= j) & 1) == 1) {
                count++;
            }
        }
        return count;
    }
}
