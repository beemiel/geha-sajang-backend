package com.incense.gehasajang.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class SignUpServiceTest {

    private SignUpService signUpService;

    @BeforeEach
    public void setUp() {
        signUpService = new SignUpService();
    }

    @Test
    @DisplayName("(서비스)이메일 체크")
    void checkEmail() throws Exception {
        //given

        //when

        //then
        assertThat(signUpService.checkEmail("email")).isTrue();
    }

    @Test
    @DisplayName("(서비스)이름 체크")
    void checkName() throws Exception {
        //given

        //when

        //then
        assertThat(signUpService.checkName("name")).isTrue();
    }

}
