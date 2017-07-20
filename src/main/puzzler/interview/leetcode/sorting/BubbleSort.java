package puzzler.interview.leetcode.sorting;

import java.util.Arrays;
import java.util.Random;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuptsov
 * @since 27/11/2016
 */
public class BubbleSort {

    Random random = new Random();

    int[] a = new int[100];

    {
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(1000);
        }
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {a}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] a) {
        int[] sorted = Arrays.copyOf(a, a.length);
        Arrays.sort(sorted);
        sort(a);
        System.out.println(Arrays.toString(a));
        assertTrue(Arrays.equals(sorted, a));
    }

    private void sort(int[] a) {
        int k = a.length;
        while (k > 1) {
            int el = a[0];
            for (int j = 1; j < k; j++) {
                if (el > a[j]) {
                    swap(a, j, j - 1);
                } else {
                    el = a[j];
                }
            }

            k--;
        }

    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
