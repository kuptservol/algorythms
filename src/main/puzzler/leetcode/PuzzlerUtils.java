package puzzler.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static int[] toIntArray(List<Integer> intList) {
        return intList.stream().mapToInt(Integer::intValue).toArray();
    }

    public static String[] A(List<String> stringList) {
        return stringList.toArray(new String[1]);
    }

    public static Integer[] intA(List<Integer> intList) {
        return intList.toArray(new Integer[1]);
    }

    public static int[] intA(Integer... ints) {
        return Arrays.stream(ints).mapToInt(Integer::intValue).toArray();
    }

    public static String[] A(Set<String> stringList) {
        return stringList.toArray(new String[1]);
    }
}
