package puzzler.spotify;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Sergey Kuptsov
 * @since 31/08/2016
 */
public class Zipfsong {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfSongs = scanner.nextInt();
        int limit = scanner.nextInt();

        Map<String, Double> songToWeight = new HashMap<>();

        for (int i = 0; i < numberOfSongs; i++) {
            int songListenCount = scanner.nextInt();
            String songName = scanner.next();

            songToWeight.put(songName, (double) songListenCount / (numberOfSongs - i));
        }

        songToWeight.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(limit)
                .forEach(stringDoubleEntry -> System.out.println(stringDoubleEntry.getKey()));

    }
}
