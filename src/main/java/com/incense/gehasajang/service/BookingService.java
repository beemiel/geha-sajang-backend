package com.incense.gehasajang.service;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRepository;
import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import com.incense.gehasajang.model.param.room.BookingRequestParam;
import com.incense.gehasajang.model.param.room.BookingRoomParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final GuestService guestService;
    private final BookingRoomInfoService bookingRoomInfoService;
    private final BookingExtraInfoService bookingExtraInfoService;
    private final HouseService houseService;

    private final Mapper mapper;

    public void addBooking(BookingRequestParam bookingRequestParam) {
        Long houseId = bookingRequestParam.getHouseId();
        String account = bookingRequestParam.getAccount();
        BookingRequestDto requestDto = bookingRequestParam.getRequest();

        House savedHouse = houseService.getHouse(houseId, account);

        Guest guest = requestDto.toGuest(mapper);
        Guest savedGuest = guestService.addGuest(guest, requestDto.getGuest());

        Booking booking = requestDto.toBooking(savedHouse, savedGuest);
        Booking savedBooking = bookingRepository.save(booking);

        BookingRoomParam bookingRoomParam = BookingRoomParam.builder()
                .checkIn(requestDto.getCheckIn())
                .checkOut(requestDto.getCheckOut())
                .booking(savedBooking)
                .bookingRoomInfos(requestDto.getBookingRooms())
                .build();
        bookingRoomInfoService.addBookingRoomInfo(bookingRoomParam);

        bookingExtraInfoService.addBookingExtraInfo(savedBooking, requestDto.getBookingExtraInfos());
    }
}
