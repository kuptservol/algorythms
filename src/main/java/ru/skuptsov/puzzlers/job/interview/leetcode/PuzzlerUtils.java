package ru.skuptsov.puzzlers.job.interview.leetcode;

import java.util.Map;

/**
 * @author Sergey Kuptsov
 * @since 25/12/2016
 */
public class PuzzlerUtils {

    public static <K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
        V v = map.get(key);
        if (v == null) {
            map.put(key, value);
            v = value;
        }

        return v;
    }
}
