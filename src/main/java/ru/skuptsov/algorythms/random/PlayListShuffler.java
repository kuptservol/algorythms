package ru.skuptsov.algorythms.random;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Sergey Kuptsov
 * @since 22/09/2016
 */
public class PlayListShuffler {

    public static <T> List<T> shuffle(List<List<T>> clusters) {
        int playlistSize = clusters.stream()
                .map(List::size)
                .reduce((size1, size2) -> size1 + size2)
                .orElse(0);

        List<T> playList = new ArrayList<>(playlistSize);
        clusters.forEach(cluster -> fillPlaylistWithCluster(cluster, playList));

        return playList;
    }

    private static <T> void fillPlaylistWithCluster(List<T> cluster, List<T> playList) {
        List<T> shuffledCluster = shuffleCluster(cluster);
    }

    private static <T> List<T> shuffleCluster(List<T> cluster) {
        int range = cluster.size();

        while (range > 0) {
            Long rollPos = Calendar.getInstance().getTimeInMillis() % range;
            T roll = cluster.get(rollPos.intValue());
            cluster.set(rollPos.intValue(), cluster.get(range - 1));
            cluster.set(range - 1, roll);
            range -= 1;
        }

        return null;
    }
}
