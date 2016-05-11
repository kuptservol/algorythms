package ru.skuptsov.puzzlers.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Сергей on 13.04.2016.
 */
public class SantaEscape {

    private static int num_of_char;
    private static int num_of_symbols;

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(SantaEscape.class.getResourceAsStream("/santaEscape")));

        String line;
        while ((line = reader.readLine()) != null) {
            processLine(line);
        }

        System.out.println(num_of_char);
        System.out.println(num_of_symbols);
        System.out.println(num_of_char - num_of_symbols);
    }

    private static void processLine(String line) {


        num_of_char += line.length();

        char[] chars = line.toCharArray();
        for (int i = 1; i < line.length() - 1; i++) {
            if (chars[i] == '\\' && i <= line.length() - 1) {
                if (chars[i + 1] == '"' || chars[i + 1] == '\\') {
                    i++;
                    num_of_symbols++;
                } else if (chars[i + 1] == 'x' && i + 3 < line.length() - 1) {

                    String s;
                    int value = Integer.parseInt(new String(new char[]{chars[i + 2], chars[i + 3]}), 16);
                    s= Character.toString ((char) value);
                    num_of_symbols += s.length();
                    i += 3;
                } else {
                    num_of_symbols++;
                }
            } else {
                num_of_symbols++;
            }
        }
    }
}
