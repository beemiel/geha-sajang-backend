package com.incense.gehasajang.domain.host;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class HostAuthKeyTest {

    @Test
    @DisplayName("만료 체크")
    void isExpired() throws Exception {
        //given
        HostAuthKey authKey = HostAuthKey.builder().expirationDate(LocalDateTime.now().minusDays(2)).build();
        HostAuthKey authKey2 = HostAuthKey.builder().expirationDate(LocalDateTime.now().plusDays(1)).build();

        //when
        boolean value = authKey.isExpired();
        boolean value2 = authKey2.isExpired();

        //then
        assertThat(value).isTrue();
        assertThat(value2).isFalse();
    }

    @Test
    @DisplayName("인증키 일치 체크")
    void isMatched() throws Exception {
        //given
        HostAuthKey authKey = HostAuthKey.builder().authKey("test").build();

        //when
        boolean value = authKey.isMatched("testtest");
        boolean value2 = authKey.isMatched("TEST");
        boolean value3 = authKey.isMatched("test");

        //then
        assertThat(value).isFalse();
        assertThat(value2).isFalse();
        assertThat(value3).isTrue();
    }

}
