package com.incense.gehasajang.domain.guest;

import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.AccessDeniedException;
import com.incense.gehasajang.exception.NotFoundDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class GuestRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Test
    @DisplayName("게스트 리스트 테스트")
    void list() throws Exception {
        //given
        House house = houseRepository.findById(1L).orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED));

        //when
        Set<Guest> savedGuests = guestRepository.findAllByNameAndBookings_House("test", house);

        //then
        assertThat(savedGuests.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게스트 찾기")
    void findGuest() throws Exception {
        //given
        House house = houseRepository.findById(1L).orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED));

        //when
        Guest guest = guestRepository.findByIdAndBookings_House(2L, house).orElseThrow(() -> new NotFoundDataException(ErrorCode.NOT_FOUND_GUEST));

        //then
        assertThat(guest.getPhoneNumber()).isEqualTo("01011111111");
    }

}
