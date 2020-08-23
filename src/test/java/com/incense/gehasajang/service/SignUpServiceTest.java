package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.HostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


class SignUpServiceTest {

    private SignUpService signUpService;

    @Mock
    private HostRepository hostRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        signUpService = new SignUpService(hostRepository);
    }

    @Test
    @DisplayName("(서비스)이메일 체크")
    void checkEmail() throws Exception {
        //given
        given(hostRepository.existsByEmail(any())).willReturn(true);

        //when

        //then
        assertThat(signUpService.checkEmail("email")).isTrue();
        verify(hostRepository).existsByEmail(any());
    }

    @Test
    @DisplayName("(서비스)이름 체크")
    void checkName() throws Exception {
        //given
        given(hostRepository.existsByNickname(any())).willReturn(true);

        //when

        //then
        assertThat(signUpService.checkName("name")).isTrue();
        verify(hostRepository).existsByNickname(any());
    }

}
