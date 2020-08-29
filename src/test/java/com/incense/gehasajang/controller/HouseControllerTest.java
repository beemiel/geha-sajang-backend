package com.incense.gehasajang.controller;

import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.host.HostRole;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseExtraInfo;
import com.incense.gehasajang.dto.house.HouseDto;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.CannotConvertException;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.exception.NumberExceededException;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.HouseService;
import com.incense.gehasajang.service.S3Service;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HouseController.class) //스프링을 가지고 테스트 함
@AutoConfigureRestDocs
class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseService houseService;

    @MockBean
    private S3Service s3Service;

    private UserAuthentication userAuthentication;

    private JwtUtil jwtUtil;

    private String jwt;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil(Keys.secretKeyFor(SignatureAlgorithm.HS256));
        jwt = jwtUtil.createToken("test@naver.com", HostRole.ROLE_MAIN.getType());
        Claims claims = jwtUtil.parseToken(jwt);
        userAuthentication = new UserAuthentication(claims);
    }

    @Test
    @DisplayName("게스트_하우스_정보를_가져온다.")
    void getHouseInfoSuccess() throws Exception {
        //given
        House house = House.builder()
                .name("게스트하우스")
                .address(new Address("city", "street", "postcode", "detail"))
                .houseExtraInfos(Arrays.asList(
                        HouseExtraInfo.builder().title("추가1").build(),
                        HouseExtraInfo.builder().title("추가2").build(),
                        HouseExtraInfo.builder().title("추가3").build()
                ))
                .build();
        given(houseService.getHouse(any(), any())).willReturn(house);

        //when
        ResultActions resultActions = successRequestHouseInfo(1L);

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("name").value("게스트하우스"));
    }

    @Test
    @DisplayName("게스트_하우스_정보를_가져오지_못한다.")
    public void getHouseInfoFail() throws Exception {
        //given
        given(houseService.getHouse(any(), any())).willThrow(new NotFoundDataException());

        //when
        ResultActions resultActions = failRequestHouseInfo(2L);

        //then
        resultActions.andExpect(status().isNotFound());
        verify(houseService).getHouse(any(), any());
    }

    @Test
    @DisplayName("하우스 정보 등록")
    public void create() throws Exception {
        //given
        String extra = "조식☆§♥♨☎픽업☆§♥♨☎저녁☆§♥♨☎장비대여";
        HouseDto houseDto = HouseDto.builder().name("게스트하우스")
                .city("시티")
                .street("스트릿")
                .postcode("우편번호")
                .detail("상세주소")
                .mainNumber("01012345678")
                .mainImage("메인 이미지")
                .build();

        //when
        ResultActions resultActions = createRequest(houseDto, extra);

        //then
        resultActions.andExpect(status().isCreated())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST),prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestPartBody("image"),
                        requestParameters(
                                parameterWithName("name").description("이름(50자이내 필수값)"),
                                parameterWithName("mainNumber").description("전화번호(숫자만, 11자이내 필수값)"),
                                parameterWithName("extra").description("게스트 하우스 추가 정보")
                        )));
        verify(houseService).addHouse(any(House.class), any(String.class), any());
    }

    @Test
    public void validation() throws Exception {
        //given
        String extra = "";
        HouseDto houseDto = HouseDto.builder()
                .name("")
                .mainNumber("01012-3456-11178")
                .build();
        //when
        ResultActions resultActions = createRequest(houseDto, extra);

        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST),prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("name").description("이름(50자이내 필수값)"),
                                parameterWithName("mainNumber").description("전화번호(숫자만, 11자이내)"),
                                parameterWithName("extra").description("게스트 하우스 추가 정보")
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
    @DisplayName("하우스 추가 정보 개수 초과")
    public void numberExceededException() throws Exception {
        //given
        String extra = "조식☆§♥♨☎석식☆§♥♨☎중식☆§♥♨☎야식☆§♥♨☎조식☆§♥♨☎석식☆§♥♨☎중식☆§♥♨☎야식☆§♥♨☎조식☆§♥♨☎석식☆§♥♨☎중식☆§♥♨☎야식☆§♥♨☎조식☆§♥♨☎석식☆§♥♨☎중식☆§♥♨☎야식☆§♥♨☎조식☆§♥♨☎석식☆§♥♨☎중식☆§♥♨☎야식☆§♥♨☎조식☆§♥♨☎석식☆§♥♨☎중식☆§♥♨☎야식☆§♥♨☎조식☆§♥♨☎석식☆§♥♨☎중식☆§♥♨☎야식☆§♥♨☎";
        HouseDto houseDto = HouseDto.builder().name("게스트하우스")
                .city("시티")
                .street("스트릿")
                .postcode("우편번호")
                .detail("상세주소")
                .mainNumber("01012345678")
                .mainImage("메인 이미지")
                .build();
        doThrow(NumberExceededException.class).when(houseService).addHouse(any(House.class), any(String.class), any());

        //when
        ResultActions resultActions = createRequest(houseDto, extra);

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(ErrorCode.NUMBER_EXCEED.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST),prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }

    @Test
    @DisplayName("하우스 이미지 파일 초과 테스트")
    public void fileSizeLimitExceededException() throws Exception {
        //given
        String extra = "조식☆§♥♨☎석식☆§♥♨☎중식☆§♥♨☎야식";
        HouseDto houseDto = HouseDto.builder().name("게스트하우스")
                .city("시티")
                .street("스트릿")
                .postcode("우편번호")
                .detail("상세주소")
                .mainNumber("01012345678")
                .mainImage("메인 이미지")
                .build();
        doThrow(MaxUploadSizeExceededException.class).when(s3Service).upload(any(MultipartFile.class), any(String.class));

        //when
        ResultActions resultActions = createRequest(houseDto, extra);

        //then
        resultActions.andExpect(status().is5xxServerError())
                .andExpect(jsonPath("code").value(ErrorCode.FILE_SIZE_LIMIT_EXCEED.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST),prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }

    @Test
    @DisplayName("하우스 이미지 파일 변환 테스트")
    public void cannotConvertException() throws Exception {
        //given
        String extra = "조식☆§♥♨☎석식☆§♥♨☎중식☆§♥♨☎야식";
        HouseDto houseDto = HouseDto.builder().name("게스트하우스")
                .city("시티")
                .street("스트릿")
                .postcode("우편번호")
                .detail("상세주소")
                .mainNumber("01012345678")
                .mainImage("메인 이미지")
                .build();
        doThrow(CannotConvertException.class).when(s3Service).upload(any(MultipartFile.class), any(String.class));

        //when
        ResultActions resultActions = createRequest(houseDto, extra);

        //then
        resultActions.andExpect(status().is5xxServerError())
                .andExpect(jsonPath("code").value(ErrorCode.CANNOT_CONVERT_FILE.getCode()))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST),prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                        )));
    }

    private ResultActions successRequestHouseInfo(Long houseId) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/houses/{houseId}", houseId)
                .with(authentication(userAuthentication))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST),prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("houseId").description("요청하고자 하는 house id")
                        ),
                        responseFields(
                                fieldWithPath("houseId").description("요청한 house id").type(Long.class),
                                fieldWithPath("name").description("house 이름"),
                                fieldWithPath("city").description("house 주소1"),
                                fieldWithPath("street").description("house 주소2"),
                                fieldWithPath("postcode").description("house 주소 우편번호"),
                                fieldWithPath("detail").description("house 주소 상세"),
                                fieldWithPath("mainImage").description("이미지(원본)"),
                                fieldWithPath("thumbnailImage").description("이미지(썸네일)"),
                                fieldWithPath("mainNumber").description("전화번호"),
                                fieldWithPath("houseExtraInfoDtos").description("게스트 하우스 추가 정보"),
                                fieldWithPath("houseExtraInfoDtos.[].houseExtraInfoId").description("추가 정보 id").optional(),
                                fieldWithPath("houseExtraInfoDtos.[].title").description("추가 정보 제목").optional()
                        )
                ));
    }

    private ResultActions failRequestHouseInfo(Long houseId) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/houses/{houseId}", houseId)
                .with(authentication(userAuthentication))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST),prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("houseId").description("요청하고자 하는 house id")
                        ),
                        responseFields(
                                fieldWithPath("message").description("에러의 상세 메세지"),
                                fieldWithPath("status").description("상태 코드"),
                                fieldWithPath("code").description("직접 정의한 에러 코드"),
                                fieldWithPath("errors").description("유효성 검사 시 에러가 나면 해당 필드안에 상세한 내용이 배열로 추가된다. 그 외의 경우에는 빈 배열로 보내진다.")
                                )
                ));
    }

    private ResultActions createRequest(HouseDto houseDto, String extra) throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image", "image/jpg", "image".getBytes());

        return mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/houses")
                .file("image",image.getBytes())
                .param("name", houseDto.getName())
                .param("mainNumber", houseDto.getMainNumber())
                .param("extra", extra)
                .with(authentication(userAuthentication))
                .contentType(MediaType.MULTIPART_FORM_DATA));

    }
}
