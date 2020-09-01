package com.incense.gehasajang.domain.guest;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingRepository;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class GuestRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    public void setUp() {
        List<House> houses = Arrays.asList(
                House.builder().name("테스트 하우스1").mainNumber("01011111111").build(),
                House.builder().name("테스트 하우스2").mainNumber("01022222222").build()
        );
        houses.forEach(house -> houseRepository.save(house));

        List<Guest> guests = Arrays.asList(
                Guest.builder().name("foo").phoneNumber("01012345678").build(),
                Guest.builder().name("foo").phoneNumber("01087654321").build(),
                Guest.builder().name("foo").phoneNumber("01011111113").build()
        );
        guests.forEach(guest -> guestRepository.save(guest));

        List<Booking> bookings = Arrays.asList(
                Booking.builder().guest(guests.get(0)).house(houses.get(1)).checkIn(LocalDateTime.now().minusDays(3)).checkOut(LocalDateTime.now()).femaleCount(2).maleCount(0).requirement("메모1").build(),
                Booking.builder().guest(guests.get(0)).house(houses.get(1)).checkIn(LocalDateTime.now().minusDays(15)).checkOut(LocalDateTime.now().minusDays(2)).femaleCount(2).maleCount(0).requirement("메모2").build(),
                Booking.builder().guest(guests.get(1)).house(houses.get(1)).checkIn(LocalDateTime.now().minusDays(5)).checkOut(LocalDateTime.now().minusDays(4)).femaleCount(2).maleCount(0).requirement("메모3").build(),
                Booking.builder().guest(guests.get(1)).house(houses.get(1)).checkIn(LocalDateTime.now().minusDays(5)).checkOut(LocalDateTime.now().minusDays(6)).femaleCount(2).maleCount(0).requirement("메모4").build(),
                Booking.builder().guest(guests.get(2)).house(houses.get(1)).checkIn(LocalDateTime.now().minusDays(5)).checkOut(LocalDateTime.now().minusDays(7)).femaleCount(2).maleCount(0).requirement("메모5").build()
        );
        bookings.forEach(booking -> bookingRepository.save(booking));
    }

    @Test
    @DisplayName("게스트 리스트 테스트")
    void list() throws Exception {
        //when
        Set<Guest> guests = guestRepository.findAllByNameAndBookings_House_Id("foo", 2L);

        //then
        assertThat(guests.size()).isEqualTo(3);
    }

}
