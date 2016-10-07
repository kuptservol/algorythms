package ru.skuptsov.algorythms.random;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static java.lang.Integer.compare;
import static java.util.Arrays.asList;

/**
 * @author Sergey Kuptsov
 * @since 22/09/2016
 * todo: It would be nice if algorythm kept track of what songs it played and
 * makes sure that all others songs play first before playing a song for the second time.
 * There are some songs that play all the time and others that seem never to play.
 */
public class PlayListShuffler {

    public static <T> List<T> shuffle(Collection<Collection<T>> clustersToSort) {
        List<Collection<T>> clusters = new ArrayList<>(clustersToSort);

        int playlistSize = clusters.stream()
                .map(Collection::size)
                .reduce((size1, size2) -> size1 + size2)
                .orElse(0);

        clusters.sort((o1, o2) -> compare(o1.size(), o2.size()));

        @SuppressWarnings("unchecked")
        T[] playList = (T[]) new Object[playlistSize];

        clusters.forEach(cluster -> fillPlaylistWithCluster(cluster, playList));

        return asList(playList);
    }

    private static <T> void fillPlaylistWithCluster(Collection<T> cluster, T[] playList) {
        Collection<T> shuffledCluster = shuffleCluster(cluster);
        int playListLength = playList.length;
        int distance = playListLength / shuffledCluster.size();

        int nextPos = getRandomNextInt(playListLength);
        for (T playEl : shuffledCluster) {
            while (playList[nextPos] != null) {
                if (nextPos == playListLength - 1)
                    nextPos = 0;
                else
                    nextPos++;
            }

            playList[nextPos] = playEl;
            if (nextPos + distance >= playListLength - 1)
                nextPos = nextPos + distance - playListLength + 1;
            else
                nextPos += distance;
        }
    }

    private static <T> Collection<T> shuffleCluster(Collection<T> cluster) {
        List<T> shuffledCluster = new ArrayList<T>(cluster);
        int range = shuffledCluster.size();

        while (range > 0) {
            int rollPos = getRandomNextInt(range);
            T roll = shuffledCluster.get(rollPos);
            shuffledCluster.set(rollPos, shuffledCluster.get(range - 1));
            shuffledCluster.set(range - 1, roll);
            range -= 1;
        }

        return shuffledCluster;
    }

    private static int getRandomNextInt(int range) {
        return new Long(Calendar.getInstance().getTimeInMillis() % range).intValue();
    }
}
