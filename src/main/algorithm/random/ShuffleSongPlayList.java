package algorithm.random;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import static com.google.common.collect.ImmutableList.of;

/**
 * @author Sergey Kuptsov
 * @since 21/09/2016
 * Pseudo-algorithm.random algorythm guarantees not repeating same artist song in algorithm.random
 * shuffle one after another immediatelly
 */
public class ShuffleSongPlayList {

    private final static List<String> userArtists = of("Iron_Maiden", "Black_Sabbath", "Led_Zeppelin");
    private final static Map<String, List<String>> artistsAlbums = ImmutableMap.of(
            "Iron_Maiden", of("album_1"),
            "Black_Sabbath", of("album_1", "album_2", "album_3"),
            "Led_Zeppelin", of("album_1")
    );

    private final static Map<String, List<String>> albumSongs = ImmutableMap.of(
            "Iron_Maiden_album_1", of("Iron_Maiden_album_1_song1"),
            "Black_Sabbath_album_1", of("Black_Sabbath_album_1_song1", "Black_Sabbath_album_1_song2", "Black_Sabbath_album_1_song3"),
//            "Black_Sabbath_album_2", of("Black_Sabbath_album_2_song1", "Black_Sabbath_album_2_song2", "Black_Sabbath_album_2_song3", "Black_Sabbath_album_2_song4", "Black_Sabbath_album_2_song5"),
//            "Black_Sabbath_album_3", of("Black_Sabbath_album_3_song1", "Black_Sabbath_album_3_song2", "Black_Sabbath_album_3_song3", "Black_Sabbath_album_3_song4"),
            "Led_Zeppelin_album_1", of("Led_Zeppelin_album_1_song1", "Led_Zeppelin_album_1_song2", "Led_Zeppelin_album_1_song3", "Led_Zeppelin_album_1_song4", "Led_Zeppelin_album_1_song5", "Led_Zeppelin_album_1_song6", "Led_Zeppelin_album_1_song7", "Led_Zeppelin_album_1_song8")
    );

    public static void main(String[] args) {


        System.out.println(PlayListShuffler.shuffle(albumSongs.values().stream().collect(Collectors.toList())));

    }

    private static List<String> shufflePlayList(List<String> songs) {
        return songs;
    }

}
