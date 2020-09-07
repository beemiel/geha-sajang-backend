package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRoomInfo;
import com.incense.gehasajang.domain.booking.BookingRoomInfoRepository;
import com.incense.gehasajang.domain.booking.Gender;
import com.incense.gehasajang.domain.room.UnbookedRoom;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import com.incense.gehasajang.model.param.booking.BookingRoomParam;
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
    private final AuthorizationService authorizationService;

    private Booking booking;

    public void addBookingRoomInfo(BookingRoomParam bookingRoomParam) {
        booking = bookingRoomParam.getSavedBooking();
        Long houseId = bookingRoomParam.getHouseId();
        List<BookingRoomRequestDto> bookingRoomInfos = bookingRoomParam.getBookingRoomInfos();

        bookingRoomInfos.forEach(bookingRoomDto -> {
            //roomId와 houseId 검증
            boolean exist = authorizationService.checkRoom(bookingRoomDto.getRoomId(), houseId);
            if (!exist) {
                throw new NotFoundDataException(ErrorCode.ROOM_NOT_FOUND);
            }

            //재고, 인원 수  확인 후 가져오기
            Map<LocalDateTime, List<UnbookedRoom>> unbookedRooms = unbookedRoomService.getUnbookedRoomsByRoomId(bookingRoomDto, booking.getStay());

            //부킹룸 저장 & 재고 삭제
            unbookedRooms.values().forEach(rooms -> addBookingRoom(rooms, bookingRoomDto));
        });
    }

    private void addBookingRoom(List<UnbookedRoom> rooms, BookingRoomRequestDto bookingRoomRequestDto) {
        int femaleCount = bookingRoomRequestDto.getFemaleCount();
        int amount = bookingRoomRequestDto.sum();

        int index = 0;
        for (UnbookedRoom unbookedRoom : rooms) {
            if (index < femaleCount) {
                addRoomByGender(unbookedRoom, Gender.FEMALE);
            }

            if (femaleCount <= index && index < amount) {
                addRoomByGender(unbookedRoom, Gender.MALE);
            }

            index++;
        }
    }

    private void addRoomByGender(UnbookedRoom unbookedRoom, Gender gender) {
        BookingRoomInfo info = BookingRoomInfo.builder()
                .booking(booking)
                .unbookedRoom(unbookedRoom)
                .gender(gender)
                .build();

        //부킹룸 저장
        bookingRoomInfoRepository.save(info);

        //재고 삭제
        addSoldStock(unbookedRoom);
    }

    private void addSoldStock(UnbookedRoom unbookedRoom) {
        unbookedRoomService.addBookedRoom(unbookedRoom);
    }

}
