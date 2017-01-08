package ru.skuptsov.puzzlers.job.interview.leetcode.stringarray;

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

    static int[] N = {1, 2, 3};
    static int[] M = {4, 5};
    static int[] MEDIAN = {1, 2, 3, 4, 5, 6, 7};
    static int result = 4;

    public static void main(String[] args) {
        int[] MN = new int[N.length + M.length];

        System.out.println(findMedianSortedArrays(N, M));
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int total = nums1.length + nums2.length;
        if (total % 2 == 0) {
            return (findKth(total / 2 + 1, nums1, nums2, 0, 0) + findKth(total / 2, nums1, nums2, 0, 0)) / 2.0;
        } else {
            return findKth(total / 2 + 1, nums1, nums2, 0, 0);
        }
    }

    public static int findKth(int k, int[] nums1, int[] nums2, int s1, int s2) {
        if (s1 >= nums1.length)
            return nums2[s2 + k - 1];

        if (s2 >= nums2.length)
            return nums1[s1 + k - 1];

        if (k == 1)
            return Math.min(nums1[s1], nums2[s2]);

        int m1 = s1 + k / 2 - 1;
        int m2 = s2 + k / 2 - 1;

        int mid1 = m1 < nums1.length ? nums1[m1] : Integer.MAX_VALUE;
        int mid2 = m2 < nums2.length ? nums2[m2] : Integer.MAX_VALUE;

        if (mid1 < mid2) {
            return findKth(k - k / 2, nums1, nums2, m1 + 1, s2);
        } else {
            return findKth(k - k / 2, nums1, nums2, s1, m2 + 1);
        }
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
