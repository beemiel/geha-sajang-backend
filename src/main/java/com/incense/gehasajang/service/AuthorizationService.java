package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRepository;
import com.incense.gehasajang.domain.room.RoomRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorizationService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public boolean isExistsRoom(Long roomId, Long houseId) {
        return roomRepository.existsByIdAndDeletedAtNullAndHouse_Id(roomId, houseId);
    }

    public void isExistsBooking(Long bookingId, String account) {
        Booking booking = bookingRepository.existsBooking(bookingId, account);

        if(booking == null) {
            throw new NotFoundDataException(ErrorCode.NOT_FOUND_DATA);
        }
    }
}
