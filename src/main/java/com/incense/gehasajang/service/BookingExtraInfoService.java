package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingExtraInfoRepository;
import com.incense.gehasajang.model.dto.booking.request.BookingExtraInfoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingExtraInfoService {

    private final BookingExtraInfoRepository bookingExtraInfoRepository;

    public void addBookingExtraInfo(Booking savedBooking, List<BookingExtraInfoRequestDto> bookingExtraInfos) {

    }
}
