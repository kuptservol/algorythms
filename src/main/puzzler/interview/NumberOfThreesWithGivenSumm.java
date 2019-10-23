package puzzler.interview;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 * number of pairs in which a[i] + b[j] + c[k] == x
 */
public class NumberOfThreesWithGivenSumm {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(0), intA(0), intA(0), 0, 1},
                {intA(-1, 6), intA(8, 1), intA(10, 1, 2, 3), 2, 1},
                {intA(1), intA(1), intA(1), 3, 1},
                {intA(1, 1, 0), intA(2, 1), intA(1), 3, 3},
                {intA(1, 2, 3, 0), intA(5, 2, 3), intA(0, 0), 5, 4}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] a, int[] b, int[] c, int x, int res) {
        assertEquals(sum3(a, b, c, x), res);
    }

    public int sum3(int[] a, int[] b, int[] c, int x) {
        int sum3 = 0;

        Arrays.sort(a);
        Arrays.sort(b);

        for (int i = 0; i < c.length; i++) {
            int est = x - c[i];

            int left = 0;
            int right = b.length - 1;
            while (left < a.length && right >= 0) {
                int sum2 = a[left] + b[right];
                if (sum2 == est) {
                    sum3++;
                    left++;
                } else if (sum2 < est) {
                    if ((left < a.length - 1 && right > 0 && a[left + 1] < b[right - 1]) || right == 0) {
                        left++;
                    } else {
                        right--;
                    }
                } else {
                    right--;
                }
            }
        }

        return sum3;
    }
}
