package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.guest.GuestRepository;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import com.incense.gehasajang.model.dto.guest.response.GuestCheckResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class GuestServiceTest {

    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        guestService = new GuestService(guestRepository);
    }

    @Test
    @DisplayName("게스트 추가")
    void addGuest() throws Exception {
        //given
        Guest guest = Guest.builder().name("foo").phoneNumber("01000000000").email("foo@gmail.com").memo("memo1").build();
        given(guestRepository.save(any())).willReturn(guest);

        //when
        Guest savedGuest = guestService.addGuest(guest, any());

        //then
        assertThat(savedGuest.getName()).isEqualTo("foo");
        assertThat(savedGuest.getEmail()).isEqualTo("foo@gmail.com");
        assertThat(savedGuest.getPhoneNumber()).isEqualTo("01000000000");
        assertThat(savedGuest.getMemo()).isEqualTo("memo1");
        verify(guestRepository).save(guest);
    }

    @Test
    @DisplayName("게스트 찾기 없음")
    void findGuestNotFound() throws Exception {
        //given
        given(guestRepository.findAllByNameAndBookings_House(any(), any())).willReturn(Collections.emptySet());

        //when
        NotFoundDataException e = assertThrows(NotFoundDataException.class,
                () -> {
                    if (guestRepository.findAllByNameAndBookings_House(any(), any()).isEmpty()) {
                        throw new NotFoundDataException(ErrorCode.NOT_FOUND_GUEST);
                    }
                });

        //then
        assertThat(e.getErrorCode().getCode()).isEqualTo(ErrorCode.NOT_FOUND_GUEST.getCode());
    }

    @Test
    @DisplayName("게스트 찾기 있음")
    void findGuestSuccess() throws Exception {
        //given
        Set<Guest> guests = new HashSet<>();
        guests.add(Guest.builder().id(1L).name("foo").phoneNumber("01011111111").memo("memo").email("foo@gmail.com").build());
        guests.add(Guest.builder().id(2L).name("foo2").phoneNumber("01022222222").memo("memo2").email("foo2@gmail.com").build());
        House house = House.builder().id(1L).name("foo house").build();

        given(guestRepository.findAllByNameAndBookings_House(any(), any())).willReturn(guests);
        given(guestRepository.findLastBookingById(any(), any())).willReturn(LocalDateTime.now());

        //when
        List<GuestCheckResponseDto> responseDtos = guestService.findGuests(house, "foo");

        //then
        assertThat(responseDtos.size()).isEqualTo(2);
    }

}
