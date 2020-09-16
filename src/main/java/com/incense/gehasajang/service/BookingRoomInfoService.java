package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRoomInfo;
import com.incense.gehasajang.domain.booking.BookingRoomInfoRepository;
import com.incense.gehasajang.domain.booking.Gender;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.domain.room.RoomType;
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
    private int femaleCount;
    private int amount;
    private int index;

    public void addBookingRoomInfo(BookingRoomParam bookingRoomParam) {
        booking = bookingRoomParam.getSavedBooking();
        Long houseId = bookingRoomParam.getHouseId();
        List<BookingRoomRequestDto> bookingRoomInfos = bookingRoomParam.getBookingRoomInfos();

        bookingRoomInfos.forEach(bookingRoomDto -> {
            boolean exist = authorizationService.isExistsRoom(bookingRoomDto.getRoomId(), houseId);
            if (!exist) {
                throw new NotFoundDataException(ErrorCode.ROOM_NOT_FOUND);
            }

            Map<LocalDateTime, List<UnbookedRoom>> unbookedRooms = unbookedRoomService.getUnbookedRoomsByRoomId(bookingRoomDto, booking.getStay());
            unbookedRooms.values().forEach(rooms -> addRoom(rooms, bookingRoomDto));
        });
    }

    private void addRoom(List<UnbookedRoom> rooms, BookingRoomRequestDto bookingRoomDto) {
        Room room = rooms.get(0).getRoom();
        femaleCount = bookingRoomDto.getFemaleCount();
        amount = bookingRoomDto.sum();
        index = 0;

        if (room.getRoomType().equals(RoomType.MULTIPLE) || room.getRoomType().equals(RoomType.SINGLE)) {
            addMultiAndSingleRoom(rooms.get(0));
            return;
        }

        if (room.getRoomType().equals(RoomType.DORMITORY)) {
            addDormitoryRoom(rooms);
        }
    }

    private void addDormitoryRoom(List<UnbookedRoom> rooms) {
        for (UnbookedRoom unbookedRoom : rooms) {
            if (index < femaleCount) {
                addRoomByGender(unbookedRoom, Gender.FEMALE);
            }

            if (femaleCount <= index && index < amount) {
                addRoomByGender(unbookedRoom, Gender.MALE);
            }

            addSoldStock(unbookedRoom);
            index++;
        }
    }

    private void addMultiAndSingleRoom(UnbookedRoom unbookedRoom) {
        for (int i = 0; i < amount; i++) {
            if(i < femaleCount) {
                addRoomByGender(unbookedRoom, Gender.FEMALE);
                continue;
            }

            addRoomByGender(unbookedRoom, Gender.MALE);
        }

        addSoldStock(unbookedRoom);
    }

    private void addRoomByGender(UnbookedRoom unbookedRoom, Gender gender) {
        BookingRoomInfo info = BookingRoomInfo.builder()
                .booking(booking)
                .unbookedRoom(unbookedRoom)
                .gender(gender)
                .build();

        bookingRoomInfoRepository.save(info);
    }

    private void addSoldStock(UnbookedRoom unbookedRoom) {
        unbookedRoomService.addBookedRoom(unbookedRoom);
    }

}
