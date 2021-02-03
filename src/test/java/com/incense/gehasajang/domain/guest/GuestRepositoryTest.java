package com.incense.gehasajang.domain.guest;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRepository;
import com.incense.gehasajang.domain.booking.Stay;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GuestRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    @DisplayName("게스트 리스트 테스트")
    void list() throws Exception {
        //given
        House house = House.builder().name("게스트 하우스").build();
        houseRepository.save(house);

        Guest guest = Guest.builder().name("test").phoneNumber("01011111111").build();
        Guest guest2 = Guest.builder().name("test2").phoneNumber("01022222222").build();
        Guest guest3 = Guest.builder().name("test").phoneNumber("01011111111").build();
        guestRepository.save(guest);
        guestRepository.save(guest2);
        guestRepository.save(guest3);

        Booking booking = Booking.builder().house(house).guest(guest).requirement("게스트 부킹1").stay(new Stay(LocalDateTime.now(), LocalDateTime.now())).build();
        Booking booking2 = Booking.builder().house(house).guest(guest2).requirement("게스트 부킹2").stay(new Stay(LocalDateTime.now(), LocalDateTime.now())).build();
        Booking booking3 = Booking.builder().house(house).guest(guest3).requirement("게스트 부킹3").stay(new Stay(LocalDateTime.now(), LocalDateTime.now())).build();
        bookingRepository.save(booking);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);

        //when
        Set<Guest> savedGuests = guestRepository.findAllByNameAndBookings_House(guest.getName(), house);

        //then
        assertThat(savedGuests.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게스트 찾기")
    void findGuest() throws Exception {
        //given
        House house = House.builder().name("게스트 하우스").build();
        houseRepository.save(house);

        Guest guest = Guest.builder().name("test").phoneNumber("01011111111").build();
        guestRepository.save(guest);

        Booking booking = Booking.builder().house(house).guest(guest).requirement("게스트 부킹1").stay(new Stay(LocalDateTime.now(), LocalDateTime.now())).build();
        bookingRepository.save(booking);

        //when
        Guest findGuest = guestRepository.findByIdAndBookings_House(guest.getId(), house).orElseThrow(() -> new NotFoundDataException(ErrorCode.NOT_FOUND_GUEST));

        //then
        assertThat(findGuest.getPhoneNumber()).isEqualTo(guest.getPhoneNumber());
    }

}
