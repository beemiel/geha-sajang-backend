package com.incense.gehasajang.domain.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoomType {

    SINGLE("일인실"),
    MULTIPLE("다인실"),
    DORMITORY("도미토리");

    private String typeName;

    private static final Map<String, RoomType> STRING_TO_ENUM = new HashMap<>();

    static {
        for (RoomType roomType : values()) {
            STRING_TO_ENUM.put(roomType.getTypeName(), roomType);
        }
    }

    public static RoomType findBy(String roomName) {
        return STRING_TO_ENUM.get(roomName);
    }
}
