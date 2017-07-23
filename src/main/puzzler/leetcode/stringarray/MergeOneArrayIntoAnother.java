package puzzler.leetcode.stringarray;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuptsov
 * @since 16/10/2016
 * Given two sorted integer arrays A and B, merge B into A as one sorted array.
 */
public class MergeOneArrayIntoAnother {
    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {new int[]{1, 4, 10, 23}, new int[]{2, 3, 13, 56, 67}, new int[]{1, 2, 3, 4, 10, 13, 23, 56, 67}},
                {new int[]{1, 4, 10, 23}, new int[]{2, 3, 13}, new int[]{1, 2, 3, 4, 10, 13, 23}}
        };
    }

    private int[] mergeWithAdditionalArray(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length)
            if (a[i] <= b[j])
                c[k++] = a[i++];
            else
                c[k++] = b[j++];

        while (i < a.length)
            c[k++] = a[i++];

        while (j < b.length)
            c[k++] = b[j++];

        return c;
    }

    private int[] merge(int[] a, int[] b) {
        return new int[0];
    }

    @Test(dataProvider = "testData")
    public void test(int[] a, int[] b, int[] c) {
        assertTrue(Arrays.equals(mergeWithAdditionalArray(a, b), c));
        assertTrue(Arrays.equals(merge(a, b), c));

    }
}
