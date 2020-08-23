package com.incense.gehasajang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incense.gehasajang.dto.host.EmailCheckDto;
import com.incense.gehasajang.dto.host.NicknameCheckDto;
import com.incense.gehasajang.service.SignUpService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
@AutoConfigureRestDocs
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignUpService signUpService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("이메일 중복 체크")
    void checkEmail() throws Exception {
        //given
        EmailCheckDto emailCheckDto = EmailCheckDto.builder().email("email@gmail.com").build();
        given(signUpService.checkEmail(anyString())).willReturn(true);

        //when
        mockMvc.perform(post("/api/v1/users/check-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailCheckDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("중복 검사 요청할 이메일")
                        )
                ));

        //then
        verify(signUpService).checkEmail(anyString());
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    void checkName() throws Exception {
        //given
        NicknameCheckDto nicknameCheckDto = NicknameCheckDto.builder().nickname("name").build();
        given(signUpService.checkName(anyString())).willReturn(true);

        //when
        mockMvc.perform(post("/api/v1/users/check-name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nicknameCheckDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("nickname").description("중복 검사 요청할 닉네임")
                        )
                ));

        //then
        verify(signUpService).checkName(anyString());
    }

}
