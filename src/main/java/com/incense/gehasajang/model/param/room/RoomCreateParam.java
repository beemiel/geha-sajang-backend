package com.incense.gehasajang.model.param.room;

import com.incense.gehasajang.domain.room.Room;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import static com.incense.gehasajang.error.ErrorCode.CONSTRUCTOR_VALUE_INVALID;

@Getter
public class RoomCreateParam {

    private final Room room;
    private final Long houseId;
    private final String account;

    @Builder
    public RoomCreateParam(Room room, Long houseId, String account) {
        Assert.notNull(room, CONSTRUCTOR_VALUE_INVALID.getMessage());

        this.room = room;
        this.houseId = houseId;
        this.account = account;
    }
}
