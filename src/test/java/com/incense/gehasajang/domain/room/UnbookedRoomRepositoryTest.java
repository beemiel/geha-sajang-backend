package com.incense.gehasajang.domain.room;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UnbookedRoomRepositoryTest {

    @Autowired
    private UnbookedRoomRepository unbookedRoomRepository;

    @Autowired
    private RoomRepository roomRepository;

    private String date1 = "2020-08-01";
    private String date2 = "2020-08-02";
    private String date3 = "2020-08-03";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    @DisplayName("재고 가져오기")
    void getUnbookedRoomByJpa() throws Exception {
        //given
        List<Room> rooms = Arrays.asList(Room.builder().name("방1").roomType(RoomType.SINGLE).peakAmount("1000").offPeakAmount("800").maxCapacity(2).defaultCapacity(2).memo("방 메모1").build(),
                Room.builder().name("방2").roomType(RoomType.DORMITORY).peakAmount("2000").offPeakAmount("6700").maxCapacity(3).defaultCapacity(5).memo("방 메모2").build());
        roomRepository.saveAll(rooms);

        List<UnbookedRoom> unbookedRooms = Arrays.asList(
                UnbookedRoom.builder().room(rooms.get(0)).todayAmount("100").entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().room(rooms.get(1)).todayAmount("100").entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().room(rooms.get(1)).todayAmount("100").entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build()
        );
        for (UnbookedRoom unbookedRoom : unbookedRooms) {
            unbookedRoomRepository.save(unbookedRoom);
        }

        //when
        List<UnbookedRoom> unbookedRooms1 = unbookedRoomRepository.findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(LocalDate.parse(date1, formatter).atStartOfDay(), LocalDate.parse(date3, formatter).atStartOfDay(), rooms.get(0).getId());
        List<UnbookedRoom> unbookedRooms2 = unbookedRoomRepository.findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(LocalDate.parse(date2, formatter).atStartOfDay(), LocalDate.parse(date3, formatter).atStartOfDay(), rooms.get(1).getId());

        //then
        assertThat(unbookedRooms1.size()).isEqualTo(1);
        assertThat(unbookedRooms2.size()).isEqualTo(2);
    }

}
