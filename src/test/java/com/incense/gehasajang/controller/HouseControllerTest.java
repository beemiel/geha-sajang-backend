package com.incense.gehasajang.controller;

import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.service.HouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
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
    void getHouseInfo() throws Exception {
        given(houseService.getHouse(1L))
                .willReturn(House.builder()
                                .name("게스트하우스")
                                .address(new Address("city", "street", "postcode", "detail"))
                                .build());

        mockMvc.perform(RestDocumentationRequestBuilders.get("/houses/{houseId}", 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("name").value("게스트하우스"))
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
                                fieldWithPath("mainNumber").description("이미지(썸네일)")
                        )
                        ));
    }
}
