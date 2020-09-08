package com.incense.gehasajang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.*;
import com.incense.gehasajang.model.dto.signin.SignInRequestDto;
import com.incense.gehasajang.model.dto.signin.SignInResponseDto;
import com.incense.gehasajang.service.SignInService;
import com.incense.gehasajang.util.CommonString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.incense.gehasajang.util.CommonString.TOKEN_COOKIE_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignInController.class)
@AutoConfigureRestDocs
class SignInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignInService signInService;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인 성공")
    void signin() throws Exception {
        //given
        SignInRequestDto requestDto = SignInRequestDto.builder().account("foo").password("foo").build();
        SignInResponseDto responseDto = SignInResponseDto.builder().accessToken("token").registerState("unregistered").nickname("foo").profileImage("foo image").build();
        given(signInService.authenticate(any(), any())).willReturn(responseDto);

        //when
        signinRequest(requestDto)
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").value("token"))
                .andExpect(jsonPath("registerState").value("unregistered"))
                .andExpect(jsonPath("nickname").value("foo"))
                .andExpect(jsonPath("profileImage").value("foo image"))
                .andExpect(cookie().exists(TOKEN_COOKIE_NAME))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("account").description("호스트 계정"),
                                fieldWithPath("password").description("계정 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("Jwt 토큰. Cookie에 담겨서 응답이 가므로 해당 필드는 신경쓰지 않으셔도 됩니다."),
                                fieldWithPath("registerState").description("등록 진행 상태"),
                                fieldWithPath("nickname").description("호스트 닉네임"),
                                fieldWithPath("profileImage").description("호스트 프로필 이미지 Url")
                        )));
    }

    @Test
    @DisplayName("로그인 실패 호스트 없음")
    void notFoundHost() throws Exception {
        //given
        SignInRequestDto requestDto = SignInRequestDto.builder().account("foo").password("foo").build();
        doThrow(new NotFoundDataException(ErrorCode.HOST_NOT_FOUND)).when(signInService).authenticate(any(), any());

        //when
        signinRequest(requestDto).andExpect(status().isNotFound()).andExpect(jsonPath("code").value(ErrorCode.HOST_NOT_FOUND.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("로그인 실패 비밀번호 틀림")
    void wrongPassword() throws Exception {
        //given
        SignInRequestDto requestDto = SignInRequestDto.builder().account("foo").password("foo").build();
        doThrow(new FailToAuthenticationException(ErrorCode.FAIL_TO_SIGN_IN)).when(signInService).authenticate(any(), any());

        //when
        signinRequest(requestDto).andExpect(status().isUnauthorized()).andExpect(jsonPath("code").value(ErrorCode.FAIL_TO_SIGN_IN.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("로그인 실패 계정 비활성화")
    void disabled() throws Exception {
        //given
        SignInRequestDto requestDto = SignInRequestDto.builder().account("foo").password("foo").build();
        doThrow(new DisabledHostException(ErrorCode.DISABLED)).when(signInService).authenticate(any(), any());

        //when
        signinRequest(requestDto).andExpect(status().isUnauthorized()).andExpect(jsonPath("code").value(ErrorCode.DISABLED.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("로그인 실패 계정 삭제")
    void deleted() throws Exception {
        //given
        SignInRequestDto requestDto = SignInRequestDto.builder().account("foo").password("foo").build();
        doThrow(new DeletedHostException(ErrorCode.DELETED)).when(signInService).authenticate(any(), any());

        //when
        signinRequest(requestDto).andExpect(status().isUnauthorized()).andExpect(jsonPath("code").value(ErrorCode.DELETED.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("로그인 실패 메일 미인증")
    void unAuthMail() throws Exception {
        //given
        SignInRequestDto requestDto = SignInRequestDto.builder().account("foo").password("foo").build();
        doThrow(new UnAuthMailException(ErrorCode.UNAUTH_MAIL)).when(signInService).authenticate(any(), any());

        //when
        signinRequest(requestDto).andExpect(status().isUnauthorized()).andExpect(jsonPath("code").value(ErrorCode.UNAUTH_MAIL.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    private ResultActions signinRequest(SignInRequestDto requestDto) throws Exception {
        return mockMvc.perform(post("/api/v1/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));
    }


}
