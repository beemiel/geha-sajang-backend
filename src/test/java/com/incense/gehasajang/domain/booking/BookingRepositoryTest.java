package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.guest.GuestRepository;
import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.host.MainHost;
import com.incense.gehasajang.domain.house.HostHouse;
import com.incense.gehasajang.domain.house.HostHouseRepository;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HostHouseRepository hostHouseRepository;

    @Test
    @DisplayName("예약 상세 가져오기")
    void getBooking() throws Exception {
        //given
        Set<BookingExtraInfo> bookingExtraInfos = new HashSet<>();
        bookingExtraInfos.add(BookingExtraInfo.builder().memo("엑스트라1").build());
        bookingExtraInfos.add(BookingExtraInfo.builder().memo("엑스트라2").build());

        Set<BookingRoomInfo> bookingRoomInfos = new HashSet<>();
        bookingRoomInfos.add(BookingRoomInfo.builder().build());
        bookingRoomInfos.add(BookingRoomInfo.builder().build());

        Guest guest = Guest.builder().name("test").phoneNumber("01011111111").build();
        guestRepository.save(guest);

        Booking booking = Booking.builder().guest(guest).bookingExtraInfos(bookingExtraInfos).bookingRoomInfos(bookingRoomInfos).requirement("뉴부킹").stay(new Stay(LocalDateTime.now(), LocalDateTime.now())).build();
        bookingRepository.save(booking);

        //when
        Booking findBooking = bookingRepository.findBooking(booking.getId()).orElseThrow(() -> new NotFoundDataException(ErrorCode.NOT_FOUND_DATA));

        //then
        assertThat(findBooking.getBookingExtraInfos().size()).isEqualTo(bookingExtraInfos.size());
        assertThat(findBooking.getBookingRoomInfos().size()).isEqualTo(bookingRoomInfos.size());
        assertThat(findBooking.getGuest().getName()).isEqualTo(guest.getName());
        assertThat(findBooking.getRequirement()).isEqualTo(booking.getRequirement());
    }

    @Test
    @DisplayName("예약 존재 여부")
    void existsBooking() throws Exception {
        //given
        Host host = MainHost.builder().account("test@gmail.com").nickname("test").password("test").build();
        Host savedHost = hostRepository.save(host);

        House house = House.builder().name("existsBooking 하우스").build();
        House savedHouse = houseRepository.save(house);

        HostHouse hostHouse = HostHouse.builder().host(savedHost).house(savedHouse).build();
        hostHouseRepository.save(hostHouse);

        Booking booking1 = Booking.builder().house(savedHouse).requirement("existsBooking1").stay(new Stay(LocalDateTime.now(), LocalDateTime.now())).build();
        Booking booking2 = Booking.builder().house(savedHouse).requirement("existsBooking2").stay(new Stay(LocalDateTime.now(), LocalDateTime.now())).build();
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        //when
        Booking result = bookingRepository.existsBooking(booking1.getId(), savedHost.getAccount());
        Booking result2 = bookingRepository.existsBooking(booking1.getId(), "null@gmail.com");
        Booking result3 = bookingRepository.existsBooking(booking2.getId() + 1L, savedHost.getAccount());
        Booking result4 = bookingRepository.existsBooking(booking2.getId() + 1L, "null@gmail.com");

        //then
        assertThat(result).isNotNull();
        assertThat(result2).isNull();
        assertThat(result3).isNull();
        assertThat(result4).isNull();
    }

}
