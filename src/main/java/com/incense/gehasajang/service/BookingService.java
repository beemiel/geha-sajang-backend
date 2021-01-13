package com.incense.gehasajang.service;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRepository;
import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import com.incense.gehasajang.model.dto.booking.response.BookingResponseDto;
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

    private final Mapper mapper;

    @Transactional(readOnly = true)
    public Booking getBooking(Long bookingId) {
        return bookingRepository.findBooking(bookingId)
                .orElseThrow(() -> new NotFoundDataException(ErrorCode.NOT_FOUND_DATA));
    }

    public void addBookingInfo(BookingRequestDto request, House house) {
        Guest guest = request.toGuest(mapper);
        Guest savedGuest = guestService.addGuest(guest, house);

        Booking booking = request.toBooking(house, savedGuest);
        Booking savedBooking = addBooking(booking);

        BookingRoomParam bookingRoomParam = BookingRoomParam.builder()
                .houseId(house.getId())
                .savedBooking(savedBooking)
                .bookingRoomInfos(request.getBookingRooms()).build();
        bookingRoomInfoService.addBookingRoomInfo(bookingRoomParam);

        BookingExtraParam bookingExtraParam = BookingExtraParam.builder()
                .houseId(house.getId())
                .savedBooking(savedBooking)
                .bookingExtraInfos(request.getBookingExtraInfos()).build();
        bookingExtraInfoService.addBookingExtraInfo(bookingExtraParam);
    }

    private Booking addBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

}
