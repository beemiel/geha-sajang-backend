package com.incense.gehasajang.domain.room;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @DisplayName("방 유효성 검사")
    void checkRoom() throws Exception {
        //when
        boolean result = roomRepository.existsByIdAndDeletedAtNullAndHouse_Id(1L, 1L);
        boolean result2 = roomRepository.existsByIdAndDeletedAtNullAndHouse_Id(5L, 1L);

        //then
        assertThat(result).isTrue();
        assertThat(result2).isFalse();
    }

}
