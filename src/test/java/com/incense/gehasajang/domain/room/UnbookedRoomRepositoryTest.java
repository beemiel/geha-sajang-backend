package com.incense.gehasajang.domain.room;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class UnbookedRoomRepositoryTest {

    @Autowired
    private UnbookedRoomRepository unbookedRoomRepository;

    private String date1 = "2020-08-01";
    private String date2 = "2020-08-02";
    private String date3 = "2020-08-03";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    @DisplayName("재고 가져오기")
    void getUnbookedRoomByJpa() throws Exception {
        //when
        List<UnbookedRoom> unbookedRooms1 = unbookedRoomRepository.findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(LocalDate.parse(date1, formatter).atStartOfDay(), LocalDate.parse(date1, formatter).atStartOfDay(), 4L);
        List<UnbookedRoom> unbookedRooms2 = unbookedRoomRepository.findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(LocalDate.parse(date1, formatter).atStartOfDay(), LocalDate.parse(date2, formatter).atStartOfDay(), 4L);
        List<UnbookedRoom> unbookedRooms3 = unbookedRoomRepository.findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(LocalDate.parse(date1, formatter).atStartOfDay(), LocalDate.parse(date3, formatter).atStartOfDay(), 4L);
        List<UnbookedRoom> unbookedRooms4 = unbookedRoomRepository.findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(LocalDate.parse(date1, formatter).atStartOfDay(), LocalDate.parse(date3, formatter).atStartOfDay(), 5L);

        //then
        assertThat(unbookedRooms1.size()).isEqualTo(1);
        assertThat(unbookedRooms2.size()).isEqualTo(2);
        assertThat(unbookedRooms3.size()).isEqualTo(3);
        assertThat(unbookedRooms4.size()).isEqualTo(0);
    }

}
