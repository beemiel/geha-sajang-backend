package com.incense.gehasajang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incense.gehasajang.domain.host.HostRole;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.domain.room.RoomType;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.model.dto.room.RoomDto;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.HouseService;
import com.incense.gehasajang.service.RoomService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RoomController.class)
@AutoConfigureRestDocs
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @MockBean
    private HouseService houseService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserAuthentication userAuthentication;

    private JwtUtil jwtUtil;

    private String jwt;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(Keys.secretKeyFor(SignatureAlgorithm.HS256));
        jwt = jwtUtil.createToken("test@naver.com", HostRole.ROLE_MAIN.getType());
        Claims claims = jwtUtil.parseToken(jwt);
        userAuthentication = new UserAuthentication(claims);
    }

    @Test
    @DisplayName("방 상세 목록 성공")
    @WithMockUser(username = "foo", roles = {"SUB", "MAIN"})
    void list() throws Exception {
        //given
        House house = House.builder().id(1L).build();
        List<Room> rooms = Arrays.asList(
                Room.builder().id(1L).name("방1").memo("방1 메모").roomType(RoomType.SINGLE).offPeakAmount("10000").peakAmount("15000").maxCapacity(1).defaultCapacity(1).house(house).build(),
                Room.builder().id(2L).name("방2").memo("방2 메모").roomType(RoomType.MULTIPLE).offPeakAmount("10000").peakAmount("15000").maxCapacity(2).defaultCapacity(3).house(house).build(),
                Room.builder().id(3L).name("방3").memo("방3 메모").roomType(RoomType.DORMITORY).offPeakAmount("10000").peakAmount("15000").maxCapacity(6).defaultCapacity(6).house(house).build()
        );
        given(roomService.getRooms(1L)).willReturn(rooms);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/houses/{houseId}/rooms", 1))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("houseId").description("요청하고자 하는 house id, 호스트가 속한 하우스가 아닐 경우 403 반환")
                        ),
                        responseFields(
                                fieldWithPath("[].roomId").description("방 id"),
                                fieldWithPath("[].name").description("방 이름"),
                                fieldWithPath("[].houseId").description("방이 속한 house id"),
                                fieldWithPath("[].memo").description("방에 대한 메모"),
                                fieldWithPath("[].roomTypeName").description("방 타입"),
                                fieldWithPath("[].maxCapacity").description("최대 인원"),
                                fieldWithPath("[].defaultCapacity").description("기본 인원"),
                                fieldWithPath("[].peakAmount").description("성수기 가격"),
                                fieldWithPath("[].offPeakAmount").description("비성수기 가격")
                        )));
    }

    @Test
    @DisplayName("방 상세 성공")
    void detail() throws Exception {
        //given
        House house = House.builder().id(1L).build();
        Room room = Room.builder().id(1L).name("방1").memo("방1 메모").roomType(RoomType.SINGLE).offPeakAmount("10000").peakAmount("15000").maxCapacity(1).defaultCapacity(1).house(house).build();
        given(roomService.getRoom(any(), any())).willReturn(room);
        given(houseService.getHouse(any(), any())).willReturn(house);

        //when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/houses/{houseId}/rooms/{roomId}", 1, 1)
                .with(authentication(userAuthentication)));

        //then
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("name").value("방1"))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("houseId").description("요청하고자 하는 house id, 호스트가 속한 하우스가 아닐 경우 403 반환"),
                                parameterWithName("roomId").description("요청하고자 하는 room id")
                        ),
                        responseFields(
                                fieldWithPath("roomId").description("방 id"),
                                fieldWithPath("name").description("방 이름"),
                                fieldWithPath("houseId").description("방이 속한 house id"),
                                fieldWithPath("memo").description("방에 대한 메모"),
                                fieldWithPath("roomTypeName").description("방 타입"),
                                fieldWithPath("maxCapacity").description("최대 인원"),
                                fieldWithPath("defaultCapacity").description("기본 인원"),
                                fieldWithPath("peakAmount").description("성수기 가격"),
                                fieldWithPath("offPeakAmount").description("비성수기 가격")
                        )));
    }

    @Test
    @DisplayName("방 생성")
    void create() throws Exception {
        //given
        List<RoomDto> roomDtos = Arrays.asList(RoomDto.builder().name("방").roomTypeName("다인실").peakAmount("10000").offPeakAmount("9000").maxCapacity(3).defaultCapacity(3).memo("방 메모").build(),
                RoomDto.builder().name("방2").roomTypeName("다인실").peakAmount("20000").offPeakAmount("5000").maxCapacity(2).defaultCapacity(2).memo("방 메모2").build());

        //when
        ResultActions resultActions = create(roomDtos, 1L);

        //then
        resultActions.andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("houseId").description("요청하고자 하는 house id, 호스트가 속한 하우스가 아닐 경우 403 반환")
                        ),
                        requestFields(
                                fieldWithPath("[]roomId").description("방 등록 요청시에는 사용하지 않습니다."),
                                fieldWithPath("[]name").description("방 이름"),
                                fieldWithPath("[]houseId").description("방 등록 요청시에는 사용하지 않습니다."),
                                fieldWithPath("[]memo").description("방에 대한 메모"),
                                fieldWithPath("[]roomTypeName").description("방 타입"),
                                fieldWithPath("[]maxCapacity").description("최대 인원"),
                                fieldWithPath("[]defaultCapacity").description("기본 인원"),
                                fieldWithPath("[]peakAmount").description("성수기 가격"),
                                fieldWithPath("[]offPeakAmount").description("비성수기 가격")
                        )));
    }

    @Test()
    @DisplayName("path variable 실패")
    void path() throws Exception {
        //given
        List<RoomDto> roomDtos = Arrays.asList(RoomDto.builder().name("방").roomTypeName("다인실").peakAmount("10000").offPeakAmount("9000").maxCapacity(3).defaultCapacity(3).memo("방 메모").build(),
                RoomDto.builder().name("방2").roomTypeName("다인실").peakAmount("20000").offPeakAmount("5000").maxCapacity(2).defaultCapacity(2).memo("방 메모2").build());

        //when
        ResultActions resultActions = create(roomDtos, -1L);

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("code").value(ErrorCode.INPUT_VALUE_INVALID.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST), prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    private ResultActions create(List<RoomDto> roomDtos, Long houseId) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/houses/{houseId}/rooms", houseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomDtos))
                .with(authentication(userAuthentication)));
    }
}
