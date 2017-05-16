package ru.skuptsov.puzzlers;

import java.util.List;
import java.util.Map;

public class Utils {

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
}
