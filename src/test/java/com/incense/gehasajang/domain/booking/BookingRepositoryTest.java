package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    @DisplayName("예약 상세 가져오기")
    void getBooking() throws Exception {
        //when
        Booking booking = bookingRepository.findBooking(1L).orElseThrow(() -> new NotFoundDataException(ErrorCode.NOT_FOUND_DATA));

        //then
        assertThat(booking.getBookingExtraInfos().size()).isEqualTo(2);
        assertThat(booking.getBookingRoomInfos().size()).isEqualTo(4);
        assertThat(booking.getGuest().getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("예약 존재 여부")
    void existsBooking() throws Exception {
        //when
        Booking result = bookingRepository.existsBooking(1L, "bluuminn@gmail.com");
        Booking result2 = bookingRepository.existsBooking(1L, "bluuminn2@gmail.com");
        Booking result3 = bookingRepository.existsBooking(4L, "bluuminn@gmail.com");
        Booking result4 = bookingRepository.existsBooking(2L, "bluuminn@gmail.com");
        Booking result5 = bookingRepository.existsBooking(3L, "bluuminn@gmail.com");

        //then
        assertThat(result).isNotNull();
        assertThat(result2).isNull();
        assertThat(result3).isNull();
        assertThat(result4).isNull();
        assertThat(result5).isNotNull();
    }

}
