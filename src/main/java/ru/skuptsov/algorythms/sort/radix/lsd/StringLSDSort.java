package ru.skuptsov.algorythms.sort.radix.lsd;

import java.util.Arrays;

/**
 * @author Sergey Kuptsov
 * @since 25/09/2016
 * O(N)
 * Exactly : W*(8N + 3R)
 */
public class StringLSDSort {

    static int W = 7;
    private static String[] given = {"1231688", "7136655", "7136655", "6942804", "6788951", "3507013", "6788951", "4366363", "6788951", "2960164", "4006609", "7136655", "9699817", "1231688"};

    public static void main(String[] args) {
        System.out.println(Arrays.toString(given));

        String[] sorted = Arrays.copyOf(given, given.length);
        sort(sorted, W);

        Arrays.sort(given);

        System.out.println(Arrays.equals(sorted, given));
    }

    public static void sort(String[] a, int W) {
        //only numbers 0..9 R = 256 for strings
        int R = 10;
        String[] aux = new String[a.length];

        for (int razr = W - 1; razr >= 0; razr--) {
            int[] count = new int[R + 1];

            System.out.println("compute frequency counts");
            for (int i = 0; i < a.length; i++) {
                int aValue = new Integer(a[i].substring(razr, razr + 1));
                count[aValue + 1]++;
                printArray(count);
            }

            System.out.println("transform count to indices");
            for (int i = 1; i < count.length; i++) {
                count[i] = count[i] + count[i - 1];
                printArray(count);
            }

            System.out.println("distribute the data");
            for (int i = 0; i < a.length; i++) {
                int aValue = new Integer(a[i].substring(razr, razr + 1));
                aux[count[aValue]++] = a[i];
            }

            printArray(aux);

            System.out.println("copy back");
            for (int i = 0; i < a.length; i++) {
                a[i] = aux[i];
            }

            printArray(a);
        }
    }

    private static <T> void printArray(T[] a) {
        for (T t : a) {
            System.out.println(t);
        }
    }

    private static void printArray(int[] a) {
        System.out.println(Arrays.toString(a));
    }
}
