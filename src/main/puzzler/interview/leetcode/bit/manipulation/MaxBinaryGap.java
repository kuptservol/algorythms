package puzzler.interview.leetcode.bit.manipulation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.lang.Math.max;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 26/03/2017
 * <p>
 * Twitter Codility Problem – Max Binary Gap
 * <p>
 * Problem: Get maximum binary Gap.
 * <p>
 * For example, 9's binary form is 1001, the gap is 2.
 */
public class MaxBinaryGap {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {9, 2}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int number, int gap) {
        assertEquals(getMaxGap(number), gap);
    }

    public int getMaxGap(int number) {
        int maxGap = 0;
        int currGap = 0;

        while (number > 0) {
            int j = number & 1;
            number >>= 1;

            if (j == 1) {
                currGap = 0;
            } else {
                currGap++;
            }

            maxGap = max(currGap, maxGap);
        }

        return maxGap;
    }
}
