package com.incense.gehasajang.domain.host;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class HostRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @DisplayName("이메일 중복 테스트")
    @CsvSource({"'maxmax@gmail.com', true",
            "'joyjoy@gmail.com', false",
            "'lean@gmail.com', false",
            "'lynn@gmail.com', true"})
    @ParameterizedTest(name = "{index}/{arguments} TEST")
    void duplicateEmail(String email, boolean expectedValue) throws Exception {
        //when
        boolean realValue = hostRepository.existsByEmailAndDeletedAtNull(email);

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
