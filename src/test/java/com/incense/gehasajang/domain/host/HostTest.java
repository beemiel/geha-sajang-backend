package com.incense.gehasajang.domain.host;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class HostTest {

    @Test
    @DisplayName("스태프 여부 테스트")
    void isSubHost() throws Exception {
        //given
        MainHost mainHost = MainHost.builder().type("main").build();
        SubHost subHost = SubHost.builder().type("sub").build();

        //when
        boolean value = mainHost.isSubHost();
        boolean value2 = subHost.isSubHost();

        //then
        assertThat(value).isFalse();
        assertThat(value2).isTrue();
    }

    @Test
    @DisplayName("삭제 여부 테스트")
    void isDeleted() throws Exception {
        //given
        MainHost mainHost = MainHost.builder().deletedAt(LocalDateTime.now()).build();
        MainHost mainHost2 = MainHost.builder().deletedAt(null).build();

        //when
        boolean value = mainHost.isDeleted();
        boolean value2 = mainHost2.isDeleted();

        //then
        assertThat(value).isTrue();
        assertThat(value2).isFalse();
    }

}
