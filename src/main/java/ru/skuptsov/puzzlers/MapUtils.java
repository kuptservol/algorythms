package ru.skuptsov.puzzlers;

import java.util.Map;

public class MapUtils {

  public static <K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
    V v = map.get(key);
    if (v == null) {
      map.put(key, value);
      v = value;
    }

    return v;
  }
}
