package com.incense.gehasajang.model.param.room;

import com.incense.gehasajang.domain.room.Room;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;

import static com.incense.gehasajang.error.ErrorCode.CONSTRUCTOR_VALUE_INVALID;

@Getter
public class RoomCreateParam {

    private final List<Room> rooms;
    private final Long houseId;
    private final String account;

    @Builder
    public RoomCreateParam(List<Room> rooms, Long houseId, String account) {
        Assert.notEmpty(rooms, CONSTRUCTOR_VALUE_INVALID.getMessage());

        this.rooms = rooms;
        this.houseId = houseId;
        this.account = account;
    }
}
