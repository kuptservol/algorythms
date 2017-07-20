package puzzler.interview.leetcode.stringarray;

import java.util.Arrays;

/**
 * @author Sergey Kuptsov
 * @since 03/09/2016
 * Rotate an array of n elements to the right by k steps.
 * <p>
 * For example, with n = 7 and k = 3,
 * the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 * How many different ways do you know to solve this problem?
 */
public class RotateArray {

    private static int n = 7;
    private static int k = 3;

    private static int[] array = {0, 1, 2, 3, 4, 5, 6};
    private static int[] array_result = {4, 5, 6, 0, 1, 2, 3};

    public static void main(String[] args) {
        check(shiftWithAdditionalArray(Arrays.copyOf(array, array.length)));

        check(shiftWithoutAdditionalArray(Arrays.copyOf(array, array.length)));

        check(shiftWithDividingAndReverseByParts(Arrays.copyOf(array, array.length)));
    }

    private static void check(int[] result) {
        System.out.println(Arrays.toString(result));
        System.out.println("Equals : " + Arrays.equals(result, array_result));
    }

    /**
     * time O(N)
     * space O(N)
     *
     * @param array
     * @return
     */
    private static int[] shiftWithAdditionalArray(int[] array) {
        int[] arrayNew = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int shift_pos = i + k;
            if (shift_pos >= array.length) {
                shift_pos = shift_pos - array.length;
            }

            arrayNew[shift_pos] = array[i];
        }

        return arrayNew;
    }

    /**
     * time O(kN)
     * space O(1)
     *
     * @param array
     * @return
     */
    private static int[] shiftWithoutAdditionalArray(int[] array) {
        for (int i = 0; i < k; i++) {
            shift_1(array);
        }

        return array;
    }

    /**
     * time O(N)
     * space O(1)
     *
     * @param array
     * @return {0, 1, 2, 3, 4, 5, 6}
     * <p>
     * {0, 1, 2, 3,
     * {            4, 5, 6}
     * <p>
     * {3, 2, 1, 0,
     * {            6, 5, 4}
     * <p>
     * {3, 2, 1, 0, 6, 5, 4}
     * <p>
     * {4, 5, 6, 0, 1, 2, 3}
     */
    private static int[] shiftWithDividingAndReverseByParts(int[] array) {
        int divide_position = array.length - k - 1;

        reverse(array, 0, divide_position);
        System.out.println(Arrays.toString(array));
        reverse(array, divide_position + 1, array.length - 1);
        System.out.println(Arrays.toString(array));
        reverse(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));

        return array;
    }

    private static void reverse(int[] arr, int start, int end) {
        for (int i = start; i < start + (end - start + 1) / 2; i++) {
            shift(arr, i, end + start - i);
        }
    }

    private static void shift_1(int[] array) {
        int remember_i = array[array.length - 1];
        for (int i = 0; i < array.length; i++) {
            int k = array[i];
            array[i] = remember_i;
            remember_i = k;
        }
    }

    private static void shift(int[] array, int i, int j) {
        int k = array[i];
        array[i] = array[j];
        array[j] = k;
    }
}
