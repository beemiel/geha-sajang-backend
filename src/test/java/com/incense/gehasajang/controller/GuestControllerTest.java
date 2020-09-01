package com.incense.gehasajang.controller;

import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.host.HostRole;
import com.incense.gehasajang.model.dto.guest.response.GuestCheckResponseDto;
import com.incense.gehasajang.model.param.room.GuestListRequestParam;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.GuestService;
import com.incense.gehasajang.util.CommonString;
import com.incense.gehasajang.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GuestController.class)
@AutoConfigureRestDocs
class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuestService guestService;

    private UserAuthentication authentication;

    private JwtUtil jwtUtil;

    private String jwt;

    private Claims claims;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(Keys.secretKeyFor(SignatureAlgorithm.HS256));
        jwt = jwtUtil.createToken("test@naver.com", HostRole.ROLE_MAIN.getType());
        claims = jwtUtil.parseToken(jwt);
        authentication = new UserAuthentication(claims);
    }

    @Test
    @DisplayName("이름으로 게스트 가져오기")
    void list() throws Exception {
        //given
        List<GuestCheckResponseDto> responseDtos = Arrays.asList(
                GuestCheckResponseDto.builder().guestId(1L).email("foo@gmail.com").memo("test1").name("foo").phoneNumber("01000000000").lastBooking(LocalDateTime.now().minusDays(3)).build(),
                GuestCheckResponseDto.builder().guestId(2L).email("foo2@gmail.com").memo("test2").name("foo2").phoneNumber("01011111111").lastBooking(LocalDateTime.now().minusDays(1)).build()
        );
        given(guestService.findGuest(any())).willReturn(responseDtos);

        //when
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/houses/{houseId}/guests", 1)
                .param("name", "foo")
                .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]name").value("foo"))
                .andExpect(jsonPath("[0]phoneNumber").value("01000000000"))
                .andExpect(jsonPath("[1]name").value("foo2"))
                .andExpect(jsonPath("[1]phoneNumber").value("01011111111"))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("houseId").description("요청하고자 하는 house id, 호스트가 속한 하우스가 아닐 경우 403 반환")
                        ),
                        responseFields(
                                fieldWithPath("[].guestId").description("게스트 id"),
                                fieldWithPath("[].name").description("게스트 이름"),
                                fieldWithPath("[].phoneNumber").description("게스트 전화번호"),
                                fieldWithPath("[].email").description("게스트 이메일"),
                                fieldWithPath("[].memo").description("게스트 메모"),
                                fieldWithPath("[].lastBooking").description("게스트의 마지막 checkIn 날짜")
                        )));
    }
}
