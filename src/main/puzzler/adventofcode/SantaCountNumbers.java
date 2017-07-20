package puzzler.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

import static java.io.StreamTokenizer.TT_EOF;
import static java.io.StreamTokenizer.TT_NUMBER;

/**
 * @author Sergey Kuptsov
 * @since 22/06/2016
 */
public class SantaCountNumbers {

    public static void main(String[] args) throws IOException {
        Reader reader = new BufferedReader(
                new InputStreamReader(SantaCountNumbers.class.getResourceAsStream("/santaJson.txt")));

        StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
        streamTokenizer.parseNumbers();

        int token;
        int count = 0;
        while ((token = streamTokenizer.nextToken()) != TT_EOF) {
            if (token == TT_NUMBER) {
                count += Double.valueOf(streamTokenizer.nval).intValue();
            }
        }

        System.out.println(count);

    }
}
