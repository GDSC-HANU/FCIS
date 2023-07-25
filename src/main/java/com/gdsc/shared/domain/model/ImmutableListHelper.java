package com.gdsc.shared.domain.model;

import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public class ImmutableListHelper {
    private ImmutableListHelper() {
    }

    public static <T> ImmutableList<T> add(T item, ImmutableList<T> list) {
        final List<T> mutableList = new LinkedList<>(list);
        mutableList.add(item);
        return ImmutableList.copyOf(mutableList);
    }
}
