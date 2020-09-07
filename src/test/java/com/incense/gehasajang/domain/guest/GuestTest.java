package com.incense.gehasajang.domain.guest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GuestTest {

    @Test
    @DisplayName("게스트 수정")
    void updateGuest() throws Exception {
        //given
        Guest guest = Guest.builder().id(1L).name("foo2").phoneNumber("01011111111").email("foo2@gmail.com").memo("memo2").build();
        Guest guestInfo = Guest.builder().name("test").phoneNumber("01012345678").email("test@gmail.com").memo("change").build();

        //when
        Guest updatedGuest = guest.changeByInfo(guestInfo);

        //then
        assertThat(updatedGuest.getName()).isEqualTo("test");
        assertThat(updatedGuest.getEmail()).isEqualTo("test@gmail.com");
        assertThat(updatedGuest.getPhoneNumber()).isEqualTo("01012345678");
        assertThat(updatedGuest.getMemo()).isEqualTo("change");
    }

}
