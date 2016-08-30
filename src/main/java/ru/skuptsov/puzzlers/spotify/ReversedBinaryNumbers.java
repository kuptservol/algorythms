package ru.skuptsov.puzzlers.spotify;

import java.util.Scanner;

/**
 * @author Sergey Kuptsov
 * @since 30/08/2016
 */
public class ReversedBinaryNumbers {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        long i = in.nextLong();
        long j = 0;

        while (i != 0) {
            j <<= 1;
            j |= (1 & i);
            i >>= 1;
        }

        System.out.println(j);
    }
}
