package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.BookingRepository;
import com.incense.gehasajang.domain.room.RoomRepository;
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

    public boolean isExistsBooking(Long houseId, Long bookingId, String account) {
        return true;
    }
}
