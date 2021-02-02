package com.incense.gehasajang.domain.host;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HostRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @BeforeEach
    void setup() {
        Host host1 = MainHost.builder().account("maxmax@4incense.com").nickname("max").password("test").build();
        Host host2 = MainHost.builder().account("joy@4incense.com").nickname("joyjoy").password("test").build();
        Host host3 = MainHost.builder().account("lena2@4incense.com").nickname("lena2").password("test").build();
        Host host4 = MainHost.builder().account("lynn@4incense.com").nickname("lynn").password("test").build();
        hostRepository.save(host1);
        hostRepository.save(host2);
        hostRepository.save(host3);
        hostRepository.save(host4);

    }

    @DisplayName("이메일 중복 테스트")
    @CsvSource({"'maxmax@4incense.com', true",
            "'joyjoy@4incense.com', false",
            "'lena@4incense.com', false",
            "'lynn@4incense.com', true"})
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

}
