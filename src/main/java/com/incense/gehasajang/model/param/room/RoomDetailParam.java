package com.incense.gehasajang.model.param.room;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomDetailParam {

    private final Long houseId;
    private final Long roomId;
    private final String account;

    @Builder
    public RoomDetailParam(Long houseId, Long roomId, String account) {
        this.houseId = houseId;
        this.roomId = roomId;
        this.account = account;
    }
}
