package ru.skuptsov.puzzlers.adventofcode;

/**
 * Created by Сергей on 14.03.2016.
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Sergey Kuptsov
 * @since 15/03/2016
 */
public class SantaFloor {


    public static void main(String[] args) throws IOException {


        Reader reader = new InputStreamReader(SantaFloor.class.getClass().getResourceAsStream("/santaInput.txt"));

        int i = 0;
        int position = 0;
        int parentheses = reader.read();
        for (; parentheses != -1; parentheses = reader.read()) {
            position++;
            if ((char) parentheses == '(')
                i++;
            if ((char) parentheses == ')')
                i--;

            if(i==-1)
                break;
        }

        System.out.println(position);
    }
}

