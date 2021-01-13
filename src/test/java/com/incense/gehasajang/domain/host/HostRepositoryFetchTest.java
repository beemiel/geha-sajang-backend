package com.incense.gehasajang.domain.host;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
@AutoConfigureTestDatabase
class HostRepositoryFetchTest {

    @Autowired
    private HostRepository hostRepository;

    @DisplayName("인증할 호스트 찾기 테스트")
    @ValueSource(strings = {"maxmax@4incense.com", "lynn@4incense.com"})
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
