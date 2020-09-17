package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.room.UnbookedRoomRepository;
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

}
