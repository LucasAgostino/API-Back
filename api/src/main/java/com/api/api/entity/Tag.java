package com.api.api.entity;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Tag {
    INTEL,
    AMD,
    GEFORCE,
    RADEON,
    MEMORIAS,
    MEMORIAS_NOTEBOOK,
    AURICULARES,
    TECLADOS,
    MOUSES,
    MONITORES,
    WIRELESS,
    WIRED;
    public static Set<Tag> getAllTags() {
        return Arrays.stream(Tag.values()).collect(Collectors.toSet());
    }
}

