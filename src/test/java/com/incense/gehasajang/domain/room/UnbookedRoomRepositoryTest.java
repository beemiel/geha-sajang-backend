package com.incense.gehasajang.domain.room;

import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class UnbookedRoomRepositoryTest {

    @Autowired
    private UnbookedRoomRepository unbookedRoomRepository;

    @Autowired
    private BookedRoomRepository bookedRoomRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private RoomRepository roomRepository;

    private String date1 = "2020-08-01";
    private String date2 = "2020-08-02";
    private String date3 = "2020-08-03";
    private String date4 = "2020-08-04";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setUp() {

        House house = House.builder().name("foo house").mainNumber("01012345678").build();
        houseRepository.save(house);

        Room room = Room.builder().roomType(RoomType.DORMITORY).house(house).name("foo room").peakAmount("10000").offPeakAmount("15000").defaultCapacity(3).maxCapacity(5).build();
        roomRepository.save(room);

        List<UnbookedRoom> unbookedRooms = Arrays.asList(
                UnbookedRoom.builder().entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date4, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date4, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date4, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date4, formatter).atStartOfDay()).room(room).todayAmount("10000").build(),
                UnbookedRoom.builder().entryDate(LocalDate.parse(date4, formatter).atStartOfDay()).room(room).todayAmount("10000").build()
        );
        unbookedRooms.forEach(unbookedRoom -> unbookedRoomRepository.save(unbookedRoom));

        List<BookedRoom> bookedRooms = Arrays.asList(
                BookedRoom.builder().unbookedRoom(unbookedRooms.get(0)).build(),
                BookedRoom.builder().unbookedRoom(unbookedRooms.get(1)).build(),
                BookedRoom.builder().unbookedRoom(unbookedRooms.get(2)).build()
        );
        bookedRooms.forEach(bookedRoom -> bookedRoomRepository.save(bookedRoom));

    }

    @Test
    @DisplayName("재고 가져오기")
    void getUnbookedRoom() throws Exception {
        //when
        List<UnbookedRoom> unbookedRooms = unbookedRoomRepository.findAllUnbooked(1L, LocalDate.parse(date1,formatter).atStartOfDay(), LocalDate.parse(date3,formatter).atStartOfDay());

        //then
        assertThat(unbookedRooms.size()).isEqualTo(12);
    }

}
