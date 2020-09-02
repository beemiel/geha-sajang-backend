package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRoomInfo;
import com.incense.gehasajang.domain.booking.BookingRoomInfoRepository;
import com.incense.gehasajang.domain.booking.Gender;
import com.incense.gehasajang.domain.room.UnbookedRoom;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import com.incense.gehasajang.model.param.booking.BookingRoomParam;
import com.incense.gehasajang.model.param.booking.RoomInfoParam;
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

    public void addBookingRoomInfo(BookingRoomParam bookingRoomParam) {
        Booking booking = bookingRoomParam.getBooking();
        List<BookingRoomRequestDto> bookingRoomInfoDtos = bookingRoomParam.getBookingRoomInfos();

        bookingRoomInfoDtos.forEach(bookingRoomDto -> {
            //재고, 인원 수  확인 후 가져오기
            UnbookedListParam unbookedListParam = UnbookedListParam.builder()
                    .checkIn(bookingRoomParam.getCheckIn())
                    .checkOut(bookingRoomParam.getCheckOut())
                    .roomId(bookingRoomDto.getRoomId())
                    .count(bookingRoomDto.sum()).build();
            Map<LocalDateTime, List<UnbookedRoom>> unbookedRooms = unbookedRoomService.getUnbookedRooms(unbookedListParam);

            //부킹룸 저장 & 재고 삭제
            RoomInfoParam infoParam = RoomInfoParam.builder()
                    .booking(booking)
                    .femaleCount(bookingRoomDto.getFemaleCount())
                    .amount(bookingRoomDto.sum())
                    .build();
            unbookedRooms.values().forEach(rooms -> {
                addBookingRoom(rooms, infoParam);
            });
        });
    }

    private void addBookingRoom(List<UnbookedRoom> rooms, RoomInfoParam roomInfoParam) {
        Booking booking = roomInfoParam.getBooking();
        int femaleCount = roomInfoParam.getFemaleCount();
        int amount = roomInfoParam.getAmount();

        int index = 0;
        for(UnbookedRoom unbookedRoom : rooms) {
            if (index < femaleCount) {
                addRoomByGender(booking, unbookedRoom, Gender.FEMALE);
            }

            if (femaleCount <= index && index < amount) {
                addRoomByGender(booking, unbookedRoom, Gender.MALE);
            }

            index++;
        }
    }

    private void addRoomByGender(Booking booking, UnbookedRoom unbookedRoom, Gender gender) {
        BookingRoomInfo info = BookingRoomInfo.builder().booking(booking).unbookedRoom(unbookedRoom).gender(gender).build();
        //부킹룸 저장
        bookingRoomInfoRepository.save(info);
        //재고 삭제
        unbookedRoomService.addBookedRoom(unbookedRoom);
    }

}
