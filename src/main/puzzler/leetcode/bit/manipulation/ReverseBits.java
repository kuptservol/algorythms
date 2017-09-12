package puzzler.leetcode.bit.manipulation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Reverse bits of a given 32 bits unsigned integer
 * <p>
 * For example, given input 43261596 (represented in binary as 00000010100101000001111010011100),
 * return 964176192 (represented in binary as 00111001011110000010100101000000).
 *
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class ReverseBits {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {43261596, 964176192},
                {1, -2147483648}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int a, int b) {
        assertEquals(reverseBits(a), b);
    }

    public int reverseBits(int n) {
        int result = 0;

        for (int i = 0; i < 32; i++) {
            int next = (n & 1) << 31 - i;
            n >>= 1;

            result |= next;
        }

        return result;
    }
}
