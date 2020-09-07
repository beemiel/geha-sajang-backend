package com.incense.gehasajang.service;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRepository;
import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import com.incense.gehasajang.model.param.booking.BookingExtraParam;
import com.incense.gehasajang.model.param.booking.BookingRoomParam;
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

    public void addBookingInfo(BookingRequestDto request, Long houseId) {
        Guest guest = request.toGuest(mapper);
        Guest savedGuest = guestService.addGuest(guest);

        House savedHouse = houseService.getHouse(houseId);

        Booking booking = request.toBooking(savedHouse, savedGuest);
        Booking savedBooking = addBooking(booking);

        BookingRoomParam bookingRoomParam = BookingRoomParam.builder()
                .houseId(houseId)
                .savedBooking(savedBooking)
                .bookingRoomInfos(request.getBookingRooms()).build();
        bookingRoomInfoService.addBookingRoomInfo(bookingRoomParam);

        BookingExtraParam bookingExtraParam = BookingExtraParam.builder()
                .houseId(houseId)
                .savedBooking(savedBooking)
                .bookingExtraInfos(request.getBookingExtraInfos()).build();
        bookingExtraInfoService.addBookingExtraInfo(bookingExtraParam);
    }

    private Booking addBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

}
