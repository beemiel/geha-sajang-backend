package com.incense.gehasajang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incense.gehasajang.domain.host.MainHost;
import com.incense.gehasajang.dto.host.EmailCheckDto;
import com.incense.gehasajang.dto.host.NicknameCheckDto;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.*;
import com.incense.gehasajang.service.S3Service;
import com.incense.gehasajang.service.SignUpService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignUpController.class)
@AutoConfigureRestDocs
@ActiveProfiles("mysql")
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignUpService signUpService;

    @MockBean
    private S3Service s3Service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    @Test
    @DisplayName("회원가입 성공")
    void join() throws Exception {
        //given
        MainHost mainHost = MainHost.builder()
                .email("test@email.com")
                .nickname("testtest")
                .password("testtest123")
                .isAgreeToMarketing(true).build();
        doCallRealMethod().when(signUpService).addHost(mainHost);

        //when
        ResultActions resultActions = createRequest(mainHost);

        //then
        resultActions.andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestPartBody("file"),
                        requestParameters(
                                parameterWithName("email").description("이메일(형식 필수)"),
                                parameterWithName("nickname").description("닉네임(2~10자 이내)"),
                                parameterWithName("password").description("문자+숫자 8~16자 이내"),
                                parameterWithName("isAgreeToMarketing").description("마케팅 약관 동의 여부")
                        )));
        verify(signUpService).addHost(any(MainHost.class));
    }

    @Test
    @DisplayName("회원가입 유효성 검사 실패")
    void join_validation() throws Exception {
        //given
        MainHost mainHost = MainHost.builder()
                .email("email")
                .nickname("a")
                .password("1")
                .isAgreeToMarketing(true).build();

        //when
        ResultActions resultActions = createRequest(mainHost);

        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("email").description("이메일(형식 필수)"),
                                parameterWithName("nickname").description("닉네임(2~10자 이내)"),
                                parameterWithName("password").description("문자+숫자 8~16자 이내"),
                                parameterWithName("isAgreeToMarketing").description("마케팅 약관 동의 여부")
                        ),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다."),
                                fieldWithPath("errors.[].field").description("에러가 난 필드 이름"),
                                fieldWithPath("errors.[].reason").description("에러 이유"),
                                fieldWithPath("errors.[].value").description("서버로 요청했던 값").optional()
                        )));
    }

    @Test
    @DisplayName("이미지 파일 크기 초과")
    void joinImageFileExceededException() throws Exception {
        //given
        MainHost mainHost = MainHost.builder()
                .email("test@email.com")
                .nickname("testtest")
                .password("testtest123")
                .isAgreeToMarketing(true).build();
        doThrow(MaxUploadSizeExceededException.class).when(s3Service).upload(any(MultipartFile.class), any(String.class));

        //when
        ResultActions resultActions = createRequest(mainHost);

        //then
        resultActions.andExpect(status().is5xxServerError())
                .andExpect(jsonPath("code").value(ErrorCode.FILE_SIZE_LIMIT_EXCEED.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }

    @Test
    @DisplayName("이미지 변환 실패")
    void joinImageCannotConvertFile() throws Exception {
        //given
        MainHost mainHost = MainHost.builder()
                .email("test@email.com")
                .nickname("testtest")
                .password("testtest123")
                .isAgreeToMarketing(true).build();
        doThrow(CannotConvertException.class).when(s3Service).upload(any(MultipartFile.class), any(String.class));

        //when
        ResultActions resultActions = createRequest(mainHost);

        //then
        resultActions.andExpect(status().is5xxServerError())
                .andExpect(jsonPath("code").value(ErrorCode.CANNOT_CONVERT_FILE.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }

    @Test
    @DisplayName("회원가입 호스트 중복")
    void joinDuplication() throws Exception {
        //given
        MainHost mainHost = MainHost.builder()
                .email("test@email.com")
                .nickname("testtest")
                .password("testtest123")
                .isAgreeToMarketing(true).build();
        doThrow(ConstraintViolationException.class).when(signUpService).addHost(any(MainHost.class));

        //when
        ResultActions resultActions = createRequest(mainHost);

        //then
        resultActions.andExpect(status().is5xxServerError())
                .andExpect(jsonPath("code").value(ErrorCode.DUPLICATE.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }

    @Test
    @DisplayName("회원가입 이메일 인증 성공")
    void joinConfirm() throws Exception {
        //given
        String email = "4incense@gmail.com";
        String authkey = "testkey";
        doNothing().when(signUpService).confirm(email, authkey);

        //when
        confirmRequest(email, authkey)
                .andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("email").description("호스트 이메일"),
                                parameterWithName("authkey").description("인증키")
                        )));

        //then
        verify(signUpService).confirm(email, authkey);
    }

    @Test
    @DisplayName("인증키 불일치")
    void failToAuth() throws Exception {
        //given
        String email = "4incense@gmail.com";
        String authkey = "testkey";
        doThrow(new FailToAuthenticationException()).when(signUpService).confirm(any(), any());

        //when
        confirmRequest(email, authkey)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value(ErrorCode.FAIL_TO_AUTH.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }
    
    @Test
    @DisplayName("인증 중복")
    void duplicateAuth() throws Exception {
        //given
        String email = "4incense@gmail.com";
        String authkey = "testkey";
        doThrow(new DuplicateAuthException()).when(signUpService).confirm(any(), any());

        //when
        confirmRequest(email, authkey)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value(ErrorCode.DUPLICATE_AUTH.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }

    @Test
    @DisplayName("인증키 만료")
    void expiration() throws Exception {
        //given
        String email = "4incense@gmail.com";
        String authkey = "testkey";
        doThrow(new ExpirationException()).when(signUpService).confirm(any(), any());

        //when
        confirmRequest(email, authkey)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value(ErrorCode.EXPIRATION_AUTH.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }
    
    @Test
    @DisplayName("호스트 없음")
    void notFoundHost() throws Exception {
        //given
        String email = "4incense@gmail.com";
        String authkey = "testkey";
        doThrow(new NotFoundDataException()).when(signUpService).confirm(any(), any());

        //when
        confirmRequest(email, authkey)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(ErrorCode.HOST_NOT_FOUND.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }
    
    @Test
    @DisplayName("메일 전송 실패")
    void FailToSendMail() throws Exception {
        //given
        String email = "4incense@gmail.com";
        String authkey = "testkey";
        doThrow(new CannotSendMailException()).when(signUpService).confirm(any(), any());

        //when
        confirmRequest(email, authkey)
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("code").value(ErrorCode.CANNOT_SEND_MAIL.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }
    
    
    
    private ResultActions createRequest(MainHost mainHost) throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("file", "image", "image/jpg", "image".getBytes());

        return mockMvc.perform(multipart("/api/v1/users")
                .file(imageFile)
                .param("email", mainHost.getEmail())
                .param("nickname", mainHost.getNickname())
                .param("password", mainHost.getPassword())
                .param("isAgreeToMarketing", mainHost.isAgreeToMarketing() + "")
                .contentType(MediaType.MULTIPART_FORM_DATA));
    }

    private ResultActions confirmRequest(String email, String authKey) throws Exception {
        return mockMvc.perform(get("/api/v1/users/auth")
                .param("email", email)
                .param("authkey", authKey));
    }

}
