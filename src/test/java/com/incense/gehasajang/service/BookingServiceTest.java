package com.incense.gehasajang.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingExtraInfo;
import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.model.dto.booking.request.BookingExtraInfoRequestDto;
import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BookingServiceTest {

    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    @Test
    @DisplayName("부킹 매핑 테스트")
    void bookingMapping() throws Exception {
        //given
        List<BookingRoomRequestDto> bookingRooms = Arrays.asList(
                BookingRoomRequestDto.builder().roomId(1L).femaleCount(2).maleCount(0).build(),
                BookingRoomRequestDto.builder().roomId(2L).femaleCount(2).maleCount(0).build(),
                BookingRoomRequestDto.builder().roomId(3L).femaleCount(0).maleCount(2).build(),
                BookingRoomRequestDto.builder().roomId(4L).femaleCount(0).maleCount(2).build()
        );
        List<BookingExtraInfoRequestDto> bookingExtraInfos = Arrays.asList(
                BookingExtraInfoRequestDto.builder().houseExtraInfoId(1L).attendDate(LocalDateTime.now()).isAttend(false).memo("참고메모").peopleCount(3).build(),
                BookingExtraInfoRequestDto.builder().houseExtraInfoId(1L).attendDate(LocalDateTime.now().plusDays(1)).isAttend(true).memo("메모2").peopleCount(2).build()
        );
        GuestRequestDto guest = GuestRequestDto.builder().email("foo@gmail.comm").name("foo").phoneNumber("01012345678").memo("메모일까").build();
        BookingRequestDto request = BookingRequestDto.builder()
                .guest(guest)
                .checkIn(LocalDateTime.now())
                .checkOut(LocalDateTime.now().plusDays(1))
                .requirement("요구사항")
                .bookingRooms(bookingRooms)
                .bookingExtraInfos(bookingExtraInfos)
                .build();

        //when
        Guest mappingGuest = request.toGuest(mapper);
        Booking booking = request.toBooking(null, null);

        //then
        assertAll(
                () -> assertThat(mappingGuest.getName()).isEqualTo("foo"),
                () -> assertThat(mappingGuest.getEmail()).isEqualTo("foo@gmail.comm"),
                () -> assertThat(mappingGuest.getPhoneNumber()).isEqualTo("01012345678"),
                () -> assertThat(mappingGuest.getMemo()).isEqualTo("메모일까"),
                () -> assertThat(mappingGuest.getId()).isNull(),
                () -> assertThat(booking.getId()).isNull(),
                () -> assertThat(booking.getCheckIn()).isNotNull(),
                () -> assertThat(booking.getCheckOut()).isNotNull(),
                () -> assertThat(booking.getFemaleCount()).isEqualTo(4),
                () -> assertThat(booking.getMaleCount()).isEqualTo(4),
                () -> assertThat(booking.getRequirement()).isEqualTo("요구사항")
        );
    }

}
