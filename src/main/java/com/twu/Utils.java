package com.twu;

import java.util.Iterator;
import java.util.Map;

public class Utils {
    public static <K, V> K getKey(Map<K, V> map, V value) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        K key = null;

        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();

            if (value.equals(entry.getValue())) {
                key = entry.getKey();
                break;
            }
        }

        return key;
    }
}
