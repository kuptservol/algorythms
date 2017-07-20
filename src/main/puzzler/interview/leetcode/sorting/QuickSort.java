package puzzler.interview.leetcode.sorting;

import java.util.Arrays;
import java.util.Random;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuptsov
 * @since 08/01/2017
 */
public class QuickSort {

    Random random = new Random();

    int[] a = new int[100];

    {
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(1000);
        }
    }

//    int[] a = {3, 5, 1, 4, 2};

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
        sort(a, 0, a.length - 1);
    }

    private void sort(int[] a, int from, int to) {
        if (to <= from) return;

        int pivot = partition(a, from, to);
        sort(a, from, pivot - 1);
        sort(a, pivot + 1, to);
    }

    private int partition(int[] a, int from, int to) {
        int i = from;
        int j = to + 1;
        int pivot = a[from];

        while (true) {
            while (a[++i] < pivot) if (i == to) break;
            while (a[--j] > pivot) if (j == from) break;
            if (i >= j) break;
            change(a, i, j);
        }

        change(a, from, j);

        return j;
    }

    private void change(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
