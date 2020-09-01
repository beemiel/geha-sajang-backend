package com.incense.gehasajang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incense.gehasajang.domain.host.HostRole;
import com.incense.gehasajang.model.dto.booking.request.BookingExtraInfoRequestDto;
import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.BookingService;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
@AutoConfigureRestDocs
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

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
    @DisplayName("예약 등록 성공")
    void successBooking() throws Exception {
        //given
        List<BookingRoomRequestDto> bookingRooms = Arrays.asList(
                BookingRoomRequestDto.builder().roomId(1L).femaleCount(2).maleCount(0).build(),
                BookingRoomRequestDto.builder().roomId(2L).femaleCount(0).maleCount(2).build()
        );
        List<BookingExtraInfoRequestDto> bookingExtraInfos = Arrays.asList(
                BookingExtraInfoRequestDto.builder().houseExtraInfoId(1L).attendDate(LocalDateTime.now()).isAttend(true).memo("메모").peopleCount(2).build(),
                BookingExtraInfoRequestDto.builder().houseExtraInfoId(1L).attendDate(LocalDateTime.now().plusDays(1)).isAttend(true).memo("메모2").peopleCount(2).build()
        );
        GuestRequestDto guest = GuestRequestDto.builder().email("foo@gmail.comm").name("foo").phoneNumber("01012345678").memo("메모일까").build();
        BookingRequestDto request = BookingRequestDto.builder()
                .guest(guest)
                .checkIn(LocalDateTime.now())
                .checkOut(LocalDateTime.now().plusDays(1))
                .requirement("요구사항")
                .bookingRooms(bookingRooms)
                .bookingExtraInfos(bookingExtraInfos)
                .build();

        //when
        ResultActions resultActions = create(request);

        //then
        resultActions.andExpect(status().isCreated()).andDo(document("{class-name}/{method-name}",
                preprocessRequest(modifyUris()
                        .scheme(CommonString.SCHEMA)
                        .host(CommonString.HOST), prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                        parameterWithName("houseId").description("요청하고자 하는 house id, 호스트가 속한 하우스가 아닐 경우 403 반환")
                ),
                requestFields(
                        fieldWithPath("bookingId").description("예약 id"),
                        fieldWithPath("checkIn").description("체크인 날짜. 필수"),
                        fieldWithPath("checkOut").description("체크아웃 날짜. 필수"),
                        fieldWithPath("requirement").description("요구사항. 500자 이내"),
                        fieldWithPath("guest").description("게스트 Request 객체"),
                        fieldWithPath("guest.guestId").description("게스트 id"),
                        fieldWithPath("guest.name").description("게스트 이름. 필수"),
                        fieldWithPath("guest.phoneNumber").description("게스트 전화번호. 필수"),
                        fieldWithPath("guest.email").description("게스트 이메일"),
                        fieldWithPath("guest.memo").description("게스트 메모. 500자 이내"),
                        fieldWithPath("bookingRooms").description("예약한 방 배열"),
                        fieldWithPath("bookingRooms[].roomId").description("선택한 방의 id. 필수"),
                        fieldWithPath("bookingRooms[].femaleCount").description("여성 인원수"),
                        fieldWithPath("bookingRooms[].maleCount").description("남성 인원수"),
                        fieldWithPath("bookingExtraInfos").description("작성된 하우스 추가 서비스 배열"),
                        fieldWithPath("bookingExtraInfos[].houseExtraInfoId").description("선택한 추가 서비스 id. 필수"),
                        fieldWithPath("bookingExtraInfos[].bookingExtraInfoId").description("예약 추가 서비스 id"),
                        fieldWithPath("bookingExtraInfos[].isAttend").description("참석여부. true==참석, false==불참, null==미정"),
                        fieldWithPath("bookingExtraInfos[].memo").description("추가 서비스 관련 메모. 200이내"),
                        fieldWithPath("bookingExtraInfos[].attendDate").description("참석일. 필수"),
                        fieldWithPath("bookingExtraInfos[].peopleCount").description("참석 인원수")
                )
        ));
    }

    private ResultActions create(BookingRequestDto request) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/houses/{houseId}/bookings", 1)
                .with(authentication(authentication))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

}
