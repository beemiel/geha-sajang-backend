package com.incense.gehasajang.domain.house;

import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.host.MainHost;
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

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HostHouseRepository hostHouseRepository;

    @Test
    @DisplayName("하우스 검증 테스트")
    void authHouse() throws Exception {
        //when
        Host host = MainHost.builder().account("test@gmail.com").nickname("test").password("test").build();
        hostRepository.save(host);

        House house = House.builder().name("authHouse 하우스").build();
        houseRepository.save(house);

        HostHouse hostHouse = HostHouse.builder().host(host).house(house).build();
        hostHouseRepository.save(hostHouse);

        House savedHouse = houseRepository.findByIdAndHostHouses_Host_Account(house.getId(), host.getAccount())
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED));

        AccessDeniedException e = assertThrows(AccessDeniedException.class,
                () -> houseRepository.findByIdAndHostHouses_Host_Account(house.getId(), "foo@gmail.com")
                        .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED)));

        //then
        assertThat(savedHouse.getName()).isEqualTo(house.getName());
        assertThat(e.getErrorCode().getCode()).isEqualTo(ErrorCode.ACCESS_DENIED.getCode());
    }

}
