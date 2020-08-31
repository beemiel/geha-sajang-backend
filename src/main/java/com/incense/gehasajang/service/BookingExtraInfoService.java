package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.BookingExtraInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingExtraInfoService {

    private final BookingExtraInfoRepository bookingExtraInfoRepository;

}
