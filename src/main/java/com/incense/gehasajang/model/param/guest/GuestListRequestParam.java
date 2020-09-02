package com.incense.gehasajang.model.param.guest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GuestListRequestParam {

    private final Long houseId;
    private final String guestName;
    private final String hostAccount;

}
