package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.guest.GuestRepository;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
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
        GuestRequestDto guestRequestDto = GuestRequestDto.builder().name("test").phoneNumber("01012345678").email("test@gmail.com").memo("change").build();
        given(guestRepository.save(any())).willReturn(guest);

        //when
        Guest savedGuest = guestService.addGuest(guest, guestRequestDto);

        //then
        assertThat(savedGuest.getName()).isEqualTo("foo");
        assertThat(savedGuest.getEmail()).isEqualTo("foo@gmail.com");
        assertThat(savedGuest.getPhoneNumber()).isEqualTo("01000000000");
        assertThat(savedGuest.getMemo()).isEqualTo("memo1");
        verify(guestRepository).save(guest);
    }

    @Test
    @DisplayName("게스트 수정")
    void updateGuest() throws Exception {
        //given
        Guest guest = Guest.builder().id(1L).name("foo2").phoneNumber("01011111111").email("foo2@gmail.com").memo("memo2").build();
        GuestRequestDto guestRequestDto = GuestRequestDto.builder().name("test").phoneNumber("01012345678").email("test@gmail.com").memo("change").build();
        Guest updatedGuest = guest.changeByInfo(guestRequestDto);
        given(guestRepository.save(any())).willReturn(updatedGuest);

        //when
        Guest savedGuest = guestService.addGuest(guest, guestRequestDto);

        //then
        assertThat(savedGuest.getName()).isEqualTo("test");
        assertThat(savedGuest.getEmail()).isEqualTo("test@gmail.com");
        assertThat(savedGuest.getPhoneNumber()).isEqualTo("01012345678");
        assertThat(savedGuest.getMemo()).isEqualTo("change");
        verify(guestRepository).save(updatedGuest);
    }

}
