package com.twu.utils;

@FunctionalInterface
public interface ForEachConsumer<T> {
    void consume(T item, int index);
}
