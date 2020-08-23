package com.incense.gehasajang.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class) //스프링을 가지고 테스트 함
@AutoConfigureRestDocs
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("이메일 중복 체크")
    public void checkEmail() throws Exception {
        //given

        //when
        mockMvc.perform(post("/api/v1/users/check-email"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        //then
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    public void checkName() throws Exception {
        //given

        //when
        mockMvc.perform(post("/api/v1/users/check-email"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        //then
    }

}
