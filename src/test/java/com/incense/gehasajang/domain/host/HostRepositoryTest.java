package com.incense.gehasajang.domain.host;

import com.incense.gehasajang.domain.house.HostHouse;
import com.incense.gehasajang.domain.house.HostHouseRepository;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.AccessDeniedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase
class HostRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HostAuthKeyRepository hostAuthKeyRepository;

    @Autowired
    private HostHouseRepository hostHouseRepository;

    @Autowired
    private HouseRepository houseRepository;

    private House house;

    @BeforeEach
    public void setUp() {
        List<Host> hosts = Arrays.asList(
                MainHost.builder().account("maxmax@gmail.com").nickname("max").password("maxmax123").isAgreeToMarketing(true).build(),
                MainHost.builder().account("joyjoy@gmail.com").nickname("joy").password("joyjoy123123").isAgreeToMarketing(false).deletedAt(LocalDateTime.now()).build(),
                MainHost.builder().account("lynn@gmail.com").nickname("lynn").password("lynn1234").isAgreeToMarketing(true).build()
        );
        hosts.forEach(host -> hostRepository.save(host));

        hosts.forEach(host -> {
            hostAuthKeyRepository.save(HostAuthKey.builder()
                    .authKey(host.getAccount())
                    .host(host)
                    .expirationDate(LocalDateTime.now().plusDays(1))
                    .build());
        });

        house = House.builder().name("foo house").mainNumber("1234567890").build();
        houseRepository.save(house);

        HostHouse hostHouse = HostHouse.builder().host(hosts.get(0)).house(house).build();
        hostHouseRepository.save(hostHouse);
    }

    @AfterEach
    public void deleteAll() {
        hostHouseRepository.deleteAll();
        hostAuthKeyRepository.deleteAll();
        hostRepository.deleteAll();
        houseRepository.deleteAll();
    }

    @DisplayName("이메일 중복 테스트")
    @CsvSource({"'maxmax@gmail.com', true",
            "'joyjoy@gmail.com', false",
            "'lean@gmail.com', false",
            "'lynn@gmail.com', true"})
    @ParameterizedTest(name = "{index}/{arguments} TEST")
    void duplicateEmail(String email, boolean expectedValue) throws Exception {
        //when
        boolean realValue = hostRepository.existsByAccountAndDeletedAtNull(email);

        //then
        assertThat(realValue).isEqualTo(expectedValue);
    }

    @DisplayName("닉네임 중복 테스트")
    @CsvSource({"'max', true",
            "'joy', false",
            "'lean', false",
            "'lynn', true"})
    @ParameterizedTest(name = "{index}/{arguments} TEST")
    void duplicateNickname(String nickname, boolean expectedValue) throws Exception {
        //when
        boolean realValue = hostRepository.existsByNicknameAndDeletedAtNull(nickname);

        //then
        assertThat(realValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("하우스 체크 200 테스트")
    void checkHouseAuth() throws Exception {
        //when
        Host savedHost = hostRepository.findByAccountAndHostHouses_House_Id("maxmax@gmail.com", house.getId())
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED));

        //then
        assertThat(savedHost.getNickname()).isEqualTo("max");
    }

    @Test
    @DisplayName("하우스 체크 403 테스트")
    void checkHouseAuthFail() throws Exception {
        //when
        AccessDeniedException e = assertThrows(AccessDeniedException.class,
                () -> hostRepository.findByAccountAndHostHouses_House_Id("maxmax@gmail.com", 1000L)
                        .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED)));

        //then
        assertThat(e.getErrorCode().getCode()).isEqualTo(ErrorCode.ACCESS_DENIED.getCode());
    }

}
