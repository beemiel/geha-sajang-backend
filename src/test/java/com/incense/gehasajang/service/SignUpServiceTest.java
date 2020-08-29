package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.HostAuthKeyRepository;
import com.incense.gehasajang.domain.host.HostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


class SignUpServiceTest {

    private SignUpService signUpService;

    @Mock
    private HostRepository hostRepository;

    @Mock
    private HostAuthKeyRepository hostAuthKeyRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        signUpService = new SignUpService(hostRepository, hostAuthKeyRepository, javaMailSender, passwordEncoder);
    }

    @Test
    @DisplayName("(서비스)이메일 체크")
    void checkEmail() throws Exception {
        //given
        given(hostRepository.existsByAccountAndDeletedAtNull(any())).willReturn(true);

        //when

        //then
        assertThat(signUpService.checkAccount("email")).isTrue();
        verify(hostRepository).existsByAccountAndDeletedAtNull(any());
    }

    @Test
    @DisplayName("(서비스)이름 체크")
    void checkName() throws Exception {
        //given
        given(hostRepository.existsByNicknameAndDeletedAtNull(any())).willReturn(true);

        //when

        //then
        assertThat(signUpService.checkName("name")).isTrue();
        verify(hostRepository).existsByNicknameAndDeletedAtNull(any());
    }

}
