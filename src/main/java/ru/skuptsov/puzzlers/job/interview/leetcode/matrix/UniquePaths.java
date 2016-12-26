package ru.skuptsov.puzzlers.job.interview.leetcode.matrix;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;

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

    public static BigInteger factorial(int n) {
        BigInteger fact = BigInteger.valueOf(1); // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {4, 4, 20},
                {3, 7, 16}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int a, int b, int c) {
        assertEquals(uniquePaths(a, b), c);
    }

    /**
     * Analytic
     * Brave idea, but don't works
     */
    public int uniquePaths3(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }
        return permutationsCount(m) + permutationsCount(n);
    }

    /**
     * O(m*n)
     * By dynamic programming
     */
    public int uniquePaths(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }
        int[][] field = new int[m][n];

        for (int i = 0; i < n; i++) {
            field[0][i] = 1;
        }

        for (int i = 0; i < m; i++) {
            field[i][0] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                field[i][j] = field[i][j - 1] + field[i - 1][j];
            }
        }

        return field[m - 1][n - 1];
    }

    /**
     * DFS
     * Huge time consumption
     */
    public int uniquePaths2(int m, int n) {
        uniquePathCounter = 0;
        yMax = m;
        xMax = n;

        int x = 1;
        int y = 1;

        walk(x, y);

        return uniquePathCounter;
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
}
