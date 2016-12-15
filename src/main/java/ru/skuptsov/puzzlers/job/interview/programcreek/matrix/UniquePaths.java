package ru.skuptsov.puzzlers.job.interview.programcreek.matrix;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static com.google.common.math.BigIntegerMath.factorial;
import static org.testng.Assert.assertEquals;

/**
 * Created by Сергей on 15.12.2016.
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * <p>
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * <p>
 * How many possible unique paths are there?
 */
public class UniquePaths {

    int uniquePathCounter = 0;
    int yMax, xMax;

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {10, 20, 1000}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int a, int b, int c) {
        assertEquals(uniquePaths(a, b), c);
    }

    /**
     * By dynamic
     * Excessive stack consumption
     */
    public int uniquePaths(int m, int n) {
        uniquePathCounter = 0;
        yMax = m;
        xMax = n;

        int x = 1;
        int y = 1;

        walk(x, y);

        return uniquePathCounter;
    }

    /**
     * Analytic
     */
    public int uniquePaths2(int m, int n) {
        return permutationsCount(m) + permutationsCount(n);
    }

    private int permutationsCount(int n) {
        int permutationsCount = 0;
        for (int i = 2; i <= n; i++) {
            permutationsCount += C(n - 1, i - 1);
        }

        return permutationsCount;
    }

    private long C(int n, int k) {
        if (n == k) return 1;
        else {
            return factorial(n).divide(factorial(k).multiply(factorial(n - k))).longValue();
        }
    }

    public static BigInteger factorial(int n) {
        BigInteger fact = BigInteger.valueOf(1); // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact=fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    private void walk(int x, int y) {
        if (x == xMax && y == yMax)
            uniquePathCounter++;
        else if (x == xMax) walk(x, ++y);
        else if (y == yMax) walk(++x, y);
        else {
            walk(++x, y);
            walk(--x, ++y);
        }
    }

    public static void main(String[] args) {
        UniquePaths paths = new UniquePaths();

        System.out.println(factorial(50));

        System.out.println(paths.uniquePaths2(36, 7));
    }

}
