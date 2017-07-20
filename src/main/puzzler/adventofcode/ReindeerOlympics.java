package puzzler.adventofcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Kuptsov
 * @since 24/07/2016
 */
public class ReindeerOlympics {

    public static void main(String[] args) {

        Map<String, Integer> distances = new HashMap<>();

        int overallTime = 2503;

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(ReindeerOlympics.class.getResourceAsStream("/reindeerOlympics.txt")));

        reader.lines().forEach(line -> {

            String[] split = line.split(" ");
            String deer = split[0];

            Integer speed = Integer.valueOf(split[3]);

            Integer speedTime = Integer.valueOf(split[6]);

            Integer restTime = Integer.valueOf(split[13]);

            int distanceCovered = 0;


            if (deer.equals("Comet")) {
                int y = 0;
            }

            int covered = overallTime / (restTime + speedTime);

            distanceCovered += covered * speed * speedTime;

            int left = overallTime % (restTime + speedTime);

            if (left < speedTime) {
                distanceCovered += speed * left;
            } else {
                distanceCovered += speed * speedTime;
            }

            distances.put(deer, distanceCovered);
        });

        System.out.println(distances);
        System.out.println(distances.entrySet().stream().map(stringIntegerEntry -> stringIntegerEntry.getValue())
                .max(Integer::compareTo));
    }
}
