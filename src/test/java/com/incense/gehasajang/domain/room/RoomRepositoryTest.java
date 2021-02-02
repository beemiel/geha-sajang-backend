package com.incense.gehasajang.domain.room;

import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Test
    @DisplayName("방 유효성 검사")
    void checkRoom() throws Exception {
        //given
        House house = House.builder().name("checkRoom 하우스").build();
        houseRepository.save(house);

        List<Room> rooms = Arrays.asList(Room.builder().house(house).name("새로 추가한 방1").roomType(RoomType.SINGLE).peakAmount("1000").offPeakAmount("800").maxCapacity(2).defaultCapacity(2).memo("방 메모1").build(),
                Room.builder().house(house).name("새로 추가한 방2").roomType(RoomType.DORMITORY).peakAmount("2000").offPeakAmount("6700").maxCapacity(3).defaultCapacity(5).memo("방 메모2").build());
        roomRepository.saveAll(rooms);

        //when
        boolean result = roomRepository.existsByIdAndDeletedAtNullAndHouse_Id(rooms.get(0).getId(), house.getId());
        boolean result2 = roomRepository.existsByIdAndDeletedAtNullAndHouse_Id(rooms.get(1).getId()+1L, house.getId());

        //then
        assertThat(result).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    @DisplayName("방 여러개 추가 검사")
    void saveRooms() throws Exception {
        //given
        House house = House.builder().name("saveRooms 하우스").build();
        houseRepository.save(house);

        List<Room> rooms = Arrays.asList(Room.builder().house(house).name("새로 추가한 방1").roomType(RoomType.SINGLE).peakAmount("1000").offPeakAmount("800").maxCapacity(2).defaultCapacity(2).memo("방 메모1").build(),
                Room.builder().house(house).name("새로 추가한 방2").roomType(RoomType.DORMITORY).peakAmount("2000").offPeakAmount("6700").maxCapacity(3).defaultCapacity(5).memo("방 메모2").build());
        roomRepository.saveAll(rooms);

        //when
        List<Room> findRooms = roomRepository.findAll();

        //then
        assertThat(findRooms.size()).isEqualTo(2);
        assertThat(findRooms.get(1).getName()).isEqualTo("새로 추가한 방2");
    }

}
