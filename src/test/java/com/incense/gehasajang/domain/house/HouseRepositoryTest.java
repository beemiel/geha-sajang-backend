package com.incense.gehasajang.domain.house;

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
        //given
        MainHost mainHost = MainHost.builder().nickname("foo").password("foofoo").account("foo@gmail.com").isPassEmailAuth(true).isAgreeToMarketing(true).build();
        MainHost mainHost2 = MainHost.builder().nickname("foo2").password("foofoo").account("foo2@gmail.com").isPassEmailAuth(true).isAgreeToMarketing(true).build();
        hostRepository.save(mainHost);
        hostRepository.save(mainHost2);

        House house = House.builder().name("foo House").build();
        House house2 = House.builder().name("foo House2").build();
        houseRepository.save(house);
        houseRepository.save(house2);

        HostHouse hostHouse = HostHouse.builder().house(house).host(mainHost).build();
        HostHouse hostHouse2 = HostHouse.builder().house(house2).host(mainHost2).build();
        hostHouseRepository.save(hostHouse);
        hostHouseRepository.save(hostHouse2);

        //when
        House savedHouse = houseRepository.findByIdAndHostHouses_Host_Account(1L, "foo@gmail.com").get();
        House savedHouse2 = houseRepository.findByIdAndHostHouses_Host_Account(2L, "foo2@gmail.com").get();
        AccessDeniedException e = assertThrows(AccessDeniedException.class, () -> houseRepository.findByIdAndHostHouses_Host_Account(1L, "foo2@gmail.com")
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED)));

        //then
        assertThat(savedHouse.getName()).isEqualTo("foo House");
        assertThat(savedHouse2.getName()).isEqualTo("foo House2");
        assertThat(e.getErrorCode().getCode()).isEqualTo(ErrorCode.ACCESS_DENIED.getCode());
    }


}
