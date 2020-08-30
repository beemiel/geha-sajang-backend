package com.incense.gehasajang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incense.gehasajang.domain.host.HostRole;
import com.incense.gehasajang.model.dto.booking.request.BookingExtraInfoRequestDto;
import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import com.incense.gehasajang.security.UserAuthentication;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
@AutoConfigureRestDocs
class BookingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

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
        resultActions.andExpect(status().isCreated());
    }

    private ResultActions create(BookingRequestDto request) throws Exception {
        return mockMvc.perform(post("/api/v1/houses/{houseId}/bookings", 1)
                .with(authentication(authentication))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

}
