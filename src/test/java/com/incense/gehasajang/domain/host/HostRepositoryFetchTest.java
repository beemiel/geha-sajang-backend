package com.incense.gehasajang.domain.host;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
@AutoConfigureTestDatabase
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class HostRepositoryFetchTest {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HostAuthKeyRepository hostAuthKeyRepository;

    @BeforeEach
    public void setUp() {
        List<Host> hosts = Arrays.asList(
                MainHost.builder().account("maxmax@gmail.com").nickname("max").password("maxmax123").isAgreeToMarketing(true).build(),
                MainHost.builder().account("joyjoy@gmail.com").nickname("joy").password("joyjoy123123").isAgreeToMarketing(true).build()
        );
        hosts.forEach(host -> hostRepository.save(host));

        hosts.forEach(host -> {
            hostAuthKeyRepository.save(HostAuthKey.builder()
                    .authKey(host.getAccount())
                    .host(host)
                    .expirationDate(LocalDateTime.now().plusDays(1))
                    .build());
        });
    }

    @AfterEach
    public void deleteAll() {
        hostAuthKeyRepository.deleteAll();
        hostRepository.deleteAll();
    }

    @ValueSource(strings = {"maxmax@gmail.com", "joyjoy@gmail.com"})
    @DisplayName("인증할 호스트 찾기 테스트")
    @ParameterizedTest(name = "{index}/{arguments} TEST")
    void findHostTest(String email) throws Exception {
        //when
        MainHost host = hostRepository.findMainHostByAccount(email).orElseGet(
                () -> MainHost.builder().account("null").build()
        );

        //then
        assertAll(
                () -> assertThat(host.getAccount()).isEqualTo(email),
                () -> assertThat(host.getDeletedAt()).isNull(),
                () -> assertThat(host.getAuthKey().getAuthKey()).isEqualTo(email),
                () -> assertThat(host.isAgreeToMarketing()).isTrue()
        );

    }

}
