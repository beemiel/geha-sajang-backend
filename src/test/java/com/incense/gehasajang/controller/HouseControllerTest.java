package com.incense.gehasajang.controller;

import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.service.HouseService;
import com.incense.gehasajang.util.ErrorMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HouseController.class) //스프링을 가지고 테스트 함
@AutoConfigureRestDocs
class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseService houseService;

    @Test
    @DisplayName("게스트_하우스_정보를_가져온다.")
    void getHouseInfoSuccess() throws Exception {
        //given
        House house = House.builder()
                .name("게스트하우스")
                .address(new Address("city", "street", "postcode", "detail"))
                .build();
        given(houseService.getHouse(1L)).willReturn(house);

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
        given(houseService.getHouse(2L)).willThrow(new NotFoundDataException(ErrorMessages.NOT_FOUND, HttpStatus.NOT_FOUND));

        //when
        ResultActions resultActions = failRequestHouseInfo(2L);

        //then
        resultActions.andExpect(status().isNotFound());
        verify(houseService).getHouse(2L);
    }

    //TODO: 2020-08-18 로그인 확인 test 작성  -lynn

    //TODO: 2020-08-18 권한 인증 test 작성  -lynn

    private ResultActions successRequestHouseInfo(Long houseId) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.get("/houses/{houseId}", houseId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
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
                                fieldWithPath("mainNumber").description("전화번호")
                        )
                ));
    }

    private ResultActions failRequestHouseInfo(Long houseId) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.get("/houses/{houseId}", houseId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("houseId").description("요청하고자 하는 house id")
                        ),
                        responseFields(
                                fieldWithPath("message").description("error message"),
                                fieldWithPath("status").description("status 코드")
                        )
                ));
    }
}
