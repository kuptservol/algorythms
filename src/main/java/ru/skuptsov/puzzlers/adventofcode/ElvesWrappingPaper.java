package ru.skuptsov.puzzlers.adventofcode;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

/**
 * @author Sergey Kuptsov
 * @since 16/03/2016
 */
public class ElvesWrappingPaper {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(ElvesWrappingPaper.class.getResourceAsStream("/presentInput.txt"));

        int overall = 0;
        while (scanner.hasNext()) {
            String nextPresent = scanner.next();
            String[] dim = nextPresent.split("x");

            int[] sides = {valueOf(dim[0]) * valueOf(dim[1]),
                    valueOf(dim[1]) * valueOf(dim[2]),
                    valueOf(dim[0]) * valueOf(dim[2])};
            overall += 2 * (sides[0] + sides[1] + sides[2]);

            overall+= Arrays.stream(sides).min().getAsInt();


        }

        System.out.println(overall);

    }
}
