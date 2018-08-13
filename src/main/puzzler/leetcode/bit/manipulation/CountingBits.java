package puzzler.leetcode.bit.manipulation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 *         Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num
 *         calculate the number of 1's in their binary representation and return them as an array.
 */
public class CountingBits {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {5, intA(0, 1, 1, 2, 1, 2)},
                {2, intA(0, 1, 1)},
                {8, intA(0, 1, 1, 2, 1, 2, 2, 3, 1)},
                {7, intA(0, 1, 1, 2, 1, 2, 2, 3)},
                {16, intA(0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int num, int[] bits) {
        List<Integer> res = Arrays.stream(countBits(num)).boxed().collect(Collectors.toList());
        System.out.println(res);
        assertEquals(
                res,
                Arrays.stream(bits).boxed().collect(Collectors.toList()));
    }

    /**
     * next block of razr bits representation is from o to razr-1 but every one + 1
     */
    public int[] countBits(int num) {
        int[] ones = new int[num + 1];

        int bitsNumber = 1;

        int i = 1;
        ones[0] = 0;
        while (i <= num) {
            for (int j = 0; j < bitsNumber && i <= num; j++, i++) {
                ones[i] = ones[j] + 1;
            }
            bitsNumber *= 2;
        }

        return ones;
    }
}
