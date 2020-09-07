package com.incense.gehasajang.service;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingExtraInfo;
import com.incense.gehasajang.domain.booking.BookingExtraInfoRepository;
import com.incense.gehasajang.domain.house.HouseExtraInfo;
import com.incense.gehasajang.domain.house.HouseExtraInfoRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.dto.booking.request.BookingExtraInfoRequestDto;
import com.incense.gehasajang.model.param.booking.BookingExtraParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingExtraInfoService {

    private final BookingExtraInfoRepository bookingExtraInfoRepository;
    private final HouseExtraInfoRepository houseExtraInfoRepository;

    private final Mapper mapper;

    public void addBookingExtraInfo(BookingExtraParam bookingExtraParam) {
        Long houseId = bookingExtraParam.getHouseId();
        Booking savedBooking = bookingExtraParam.getSavedBooking();
        List<BookingExtraInfoRequestDto> bookingExtraInfos = bookingExtraParam.getBookingExtraInfos();

        bookingExtraInfos.forEach(info -> {
            Long houseExtraInfoId = info.getHouseExtraInfoId();
            HouseExtraInfo houseExtraInfo = houseExtraInfoRepository.findByIdAndHouse_Id(houseExtraInfoId, houseId)
                    .orElseThrow(() -> new NotFoundDataException(ErrorCode.NOT_FOUND_HOUSE_EXTRA));

            BookingExtraInfo bookingExtraInfo = info.toBookingExtraInfos(mapper);
            bookingExtraInfo.addBooking(savedBooking);
            bookingExtraInfo.addHouseExtraInfo(houseExtraInfo);

            bookingExtraInfoRepository.save(bookingExtraInfo);
        });
    }
}
