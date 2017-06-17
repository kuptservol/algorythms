package ru.skuptsov.puzzlers.codingame;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Сергей on 14.06.2017.
 */
public class Temperatures {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of temperatures to analyse
        if (in.hasNextLine()) {
            in.nextLine();
        }


        int closestVal = getClosestVal(in);

        // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");

                System.out.println(closestVal);
    }

    private static int getClosestVal(Scanner in) {
        String temps = in.nextLine(); // the n temperatures expressed as integers ranging from -273 to 5526

        if(temps.isEmpty()){
            return 0;
        }

        List<Integer> temperatures = Arrays.stream(temps.split(" ")).map(Integer::valueOf).collect(Collectors.toList());

        int diff = Integer.MAX_VALUE;
        int closestVal = Integer.MAX_VALUE;

        for (Integer temperature : temperatures) {

            if(temperature==0) {
                closestVal = 0;
                break;
            }
            int currPosDiff = (temperature < 0) ? -temperature : temperature;
            if(currPosDiff < diff || (currPosDiff==diff && temperature >0)) {
                closestVal = temperature;
                diff = currPosDiff;
            }
        }
        return closestVal;
    }
}
