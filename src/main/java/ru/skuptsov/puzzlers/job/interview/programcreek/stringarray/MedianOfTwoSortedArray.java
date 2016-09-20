package ru.skuptsov.puzzlers.job.interview.programcreek.stringarray;

import java.util.Random;

/**
 * @author Sergey Kuptsov
 * @since 16/09/2016
 * There are two sorted arrays A and B of size m and n respectively.
 * Find the median of the two sorted arrays.
 * The overall run time complexity should be O(log (m+n)).
 */
public class MedianOfTwoSortedArray {

    static Random rand = new Random();

    static int[] N = {1, 2, 4, 5, 7};
    static int[] M = {1, 3, 6};
    static int[] MEDIAN = {1, 2, 3, 4, 5, 6, 7};
    static int result = 4;

    public static void main(String[] args) {
        int[] MN = new int[N.length + M.length];

    }

    protected static void generateTestValue() {
        int m = rand.nextInt(50);
        int n = rand.nextInt(50);

        int[] N = new int[n];
        int[] M = new int[m];

        for (int i : N) {
            N[i] = rand.nextInt(50);
        }

        for (int i : M) {
            M[i] = rand.nextInt(50);
        }
    }
}
