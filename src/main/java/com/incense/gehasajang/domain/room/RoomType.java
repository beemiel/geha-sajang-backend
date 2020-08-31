package com.incense.gehasajang.domain.room;

import java.util.HashMap;
import java.util.Map;

public enum RoomType {
    SINGLE, MULTIPLE, DORMITORY;

    private static final Map<String, RoomType> STRING_TO_ENUM = new HashMap<>();

    static {
        for (RoomType sizeType : values()) {
            STRING_TO_ENUM.put(sizeType.name(), sizeType);
        }
    }

    public static RoomType findBy(String size) {
        return STRING_TO_ENUM.get(size);
    }
}
