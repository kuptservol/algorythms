package ru.skuptsov.puzzlers.job.interview.codility.toptal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuptsov
 * @since 04/04/2017
 */
public class VendingMachine {
    //1c, 5c, 10c, 25c, 50c, and 100

    final int[] nominals = {1, 5, 10, 25, 50, 100};
    int[] result1 = {1, 0, 0, 0, 0, 4};
    int[] result2 = {0, 1, 1, 0, 0, 1};
    int[] result3 = {1, 0, 1, 1, 1, 0};
    int[] result4 = {1, 0, 1, 0, 0, 0};

    public static void main(String[] args) {
        System.out.println((int) 11.899999f);
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {5f, 0.99f, result1},
                {3.14f, 1.99f, result2},
                {4f, 3.14f, result3},
                {0.45f, 0.34f, result4}
        };
    }

    @Test(dataProvider = "testData")
    public void test(float M, float N, int[] result) {
        assertTrue(Arrays.equals(getChange(M, N), result));
    }

    private int[] getChange(float M, float P) {
        int cash_amount = (int) (M * 100 - P * 100);

        int[] result = new int[6];

        for (int i = nominals.length - 1; i >= 0; i--) {
            int nomCache = cash_amount / nominals[i];
            result[i] = nomCache;
            cash_amount = cash_amount - nomCache * nominals[i];
        }

        return result;
    }
}