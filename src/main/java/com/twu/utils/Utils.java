package com.twu.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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

    public static <T> void streamForEach(Stream<T> stream, ForEachConsumer<T> consumer) {
        final AtomicInteger i = new AtomicInteger(0);

        stream.forEach(item -> consumer.consume(item, i.getAndIncrement()));
    }
}
