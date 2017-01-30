package ru.skuptsov.puzzlers.job.interview.leetcode.math;

/**
 * Determine whether an integer is a palindrome. Do this without extra space.
 */
public class IntegerPalindrome {

    public boolean isPalindrome(int i) {

        int result = 0;
        int number = 1;
        int central=0;

        int div = 1;
        while (i / div > 10) {
            div *= 10;
            number++;
        }

        boolean isOdd = number % 2 != 0;
        int middleE = number/2;

        System.out.println(div);

        for (int j = 0; j < 10; j++) {
            int k = i % 10;
            i /= 10;

            result = result ^ k;

            if(isOdd){
                if(j==middleE){
                    central=k;
                }
            }
        }

        System.out.println(result);

        return result == central;
    }

    public static void main(String[] args) {
        IntegerPalindrome integerPalindrome = new IntegerPalindrome();

        System.out.println(integerPalindrome.isPalindrome(123321));
    }
}
