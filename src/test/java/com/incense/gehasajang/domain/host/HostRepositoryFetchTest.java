package com.incense.gehasajang.domain.host;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
// TODO: 2021-02-03 아래 어노테이션이 왜 있어야 하는지 알아보기
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class HostRepositoryFetchTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HostAuthKeyRepository hostAuthKeyRepository;

    @DisplayName("인증할 호스트 찾기 테스트")
    @ValueSource(strings = {"maxmax@4incense.com", "lynn@4incense.com"})
    @ParameterizedTest(name = "{index}/{arguments} TEST")
    void findHostTest(String email) throws Exception {
        //given
        Host host = MainHost.builder().account(email).nickname(email).password("test1").isAgreeToMarketing(true).build();
        Host savedHost = hostRepository.save(host);

        HostAuthKey hostAuthKey = HostAuthKey.builder().host(savedHost).expirationDate(LocalDateTime.now().plusDays(1)).authKey(email).build();
        hostAuthKeyRepository.save(hostAuthKey);

        //when
        MainHost findHost = hostRepository.findMainHostByAccount(email).orElseGet(
                () -> MainHost.builder().account("null").build()
        );

        //then
        assertAll(
                () -> assertThat(findHost.getAccount()).isEqualTo(email),
                () -> assertThat(findHost.getDeletedAt()).isNull(),
                () -> assertThat(findHost.getAuthKey().getAuthKey()).isEqualTo(email),
                () -> assertThat(findHost.isAgreeToMarketing()).isTrue()
        );
    }

}
