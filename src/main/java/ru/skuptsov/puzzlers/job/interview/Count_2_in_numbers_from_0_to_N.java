package ru.skuptsov.puzzlers.job.interview;

/**
 * @author Sergey Kuptsov
 * @since 02/05/2016
 */
public class Count_2_in_numbers_from_0_to_N {

    public static void main(String[] args) {
        int N = 146277;
        System.out.println("test: " + test(N));
        System.out.println("result: " + count_2(N));
    }

    private static int count_2(int N) {
        int count = 0;
        int value = N;
        int digit = 0;

        while (value > 0) {
            int diff = value % 10;

            count += diff * digit * Math.pow(10, digit - 1);
            if (diff >= 3) {
                count += Math.pow(10, digit);
            } else if (diff == 2) {
                count += N % Math.pow(10, digit) + 1;
            }

            digit++;
            value = (value - diff) / 10;
        }

        return count;
    }


    public static int test(int N) {
        int count = 0;
        for (int i = 0; i <= N; i++) {
            for (char c : String.valueOf(i).toCharArray()) {
                if (c == '2') {
                    count++;
                }
            }

        }

        return count;
    }
}
