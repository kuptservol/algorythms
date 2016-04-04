package ru.skuptsov.puzzlers.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Sergey Kuptsov
 * @since 05/04/2016
 */
public class LightsOn {


    public static final int DESK_SIZE = 1000;
    private static int[][] lightDesk = new int[DESK_SIZE][DESK_SIZE];

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < DESK_SIZE; i++) {
            for (int j = 0; j < DESK_SIZE; j++) {
                lightDesk[i][j] = 0;
            }
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(LightsOn.class.getResourceAsStream("/lightOnCfg")));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            processLine(line);
        }
    }

    private static void processLine(String line) {
        String[] tokens = line.split(" ");
        System.out.println(tokens);
    }
}
