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
    private String date3 = "2020-08-03";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    @DisplayName("재고 가져오기")
    void getUnbookedRoom() throws Exception {
        //when
        List<UnbookedRoom> unbookedRooms = unbookedRoomRepository.findAllUnbooked(1L, LocalDate.parse(date1, formatter).atStartOfDay(), LocalDate.parse(date3, formatter).atStartOfDay());

        //then
        assertThat(unbookedRooms.size()).isEqualTo(15);
    }

}
