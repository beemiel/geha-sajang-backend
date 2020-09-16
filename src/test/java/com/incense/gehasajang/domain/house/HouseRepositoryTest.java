package com.incense.gehasajang.domain.house;

import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.AccessDeniedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase
class HouseRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @Test
    @DisplayName("하우스 검증 테스트")
    void authHouse() throws Exception {
        //when
        House savedHouse = houseRepository.findByIdAndHostHouses_Host_Account(1L, "bluuminn@gmail.com")
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED));

        AccessDeniedException e = assertThrows(AccessDeniedException.class,
                () -> houseRepository.findByIdAndHostHouses_Host_Account(2L, "foo@gmail.com")
                        .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED)));

        //then
        assertThat(savedHouse.getName()).isEqualTo("게스트 하우스1");
        assertThat(e.getErrorCode().getCode()).isEqualTo(ErrorCode.ACCESS_DENIED.getCode());
    }

}
