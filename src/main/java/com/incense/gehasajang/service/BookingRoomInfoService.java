package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.BookingRoomInfoRepository;
import com.incense.gehasajang.model.param.room.BookingRoomParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingRoomInfoService {

    private final BookingRoomInfoRepository bookingRoomInfoRepository;

    private final UnbookedRoomService unbookedRoomService;

    //재고 확인 AND 재고 가져오기 -> 부킹 룸 저장 -> 북드룸 추가
    public void addBookingRoomInfo(BookingRoomParam bookingRoomParam) {

    }

}
