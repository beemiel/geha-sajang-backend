package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRoomInfoRepository;
import com.incense.gehasajang.domain.room.UnbookedRoom;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import com.incense.gehasajang.model.param.booking.BookingRoomParam;
import com.incense.gehasajang.model.param.unbooked.UnbookedListParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingRoomInfoService {

    private final BookingRoomInfoRepository bookingRoomInfoRepository;

    private final UnbookedRoomService unbookedRoomService;

    //재고 인원수 확인 AND 재고 가져오기 -> 부킹 룸 저장 -> 북드룸 추가
    public void addBookingRoomInfo(BookingRoomParam bookingRoomParam) {
        LocalDateTime checkIn = bookingRoomParam.getCheckIn();
        LocalDateTime checkOut = bookingRoomParam.getCheckOut();
        Booking booking = bookingRoomParam.getBooking();
        List<BookingRoomRequestDto> bookingRoomInfoDtos = bookingRoomParam.getBookingRoomInfos();

        bookingRoomInfoDtos.forEach(bookingRoomDto -> {
            UnbookedListParam unbookedListParam = UnbookedListParam.builder().
                    checkIn(checkIn)
                    .checkOut(checkOut)
                    .roomId(bookingRoomDto.getRoomId())
                    .count(bookingRoomDto.sum()).build();
            //재고, 인원 수  확인
            Map<LocalDateTime, List<UnbookedRoom>> unbookedRooms = unbookedRoomService.getUnbookedRooms(unbookedListParam);

        });
    }

}
