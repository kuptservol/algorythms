package ru.skuptsov.puzzlers.job.interview;

/**
 * @author Sergey Kuptsov
 * @since 24/07/2016
 */
public class ChangeTwoNumbersWithoutThird {

    public static void main(String[] args) {

        int a = 25;
        int b = 466;

        b = a + b;
        a = b - a;
        b = b - a;

        System.out.println(a + "=466 " + b + "=25");

        a = 25;
        b = 466;

        b = a ^ b;
        a = b ^ a;
        b = b ^ a;

        System.out.println(a + "=466 " + b + "=25");
    }
}
