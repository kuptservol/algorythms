package ru.skuptsov.puzzlers.job.interview.programcreek.sorting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuptsov
 * @since 27/11/2016
 */
public class MergeSort {

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
        int[] aux = new int[a.length];
        sort(a, 0, a.length - 1, aux);
    }

    private void sort(int[] a, int lo, int hi, int[] aux) {
        if (hi <= lo)
            return;
        int mid = lo + (hi - lo) / 2;

        sort(a, lo, mid, aux);
        sort(a, mid + 1, hi, aux);
        merge(a, lo, mid, hi, aux);
    }

    private void merge(int[] a, int lo, int mid, int hi, int[] aux) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[i] < aux[j]) a[k] = aux[i++];
            else a[k] = aux[j++];
        }
    }
}
