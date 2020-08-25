package com.incense.gehasajang.controller;

import com.incense.gehasajang.domain.terms.Terms;
import com.incense.gehasajang.domain.terms.TermsRepository;
import com.incense.gehasajang.util.CommonString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TermsController.class)
@AutoConfigureRestDocs
class TermsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TermsRepository termsRepository;

    @Test
    @DisplayName("약관 리스트 가져오기")
    public void list() throws Exception {
        //given
        List<Terms> terms = Arrays.asList(
                Terms.builder().type("이용약관").contents("이용약관 내용").build(),
                Terms.builder().type("개인정보").contents("개인정보 내용").build(),
                Terms.builder().type("마케팅").contents("마케팅 내용").build()
        );
        given(termsRepository.findAll()).willReturn(terms);

        //when
        mockMvc.perform(get("/api/v1/terms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]type").value("이용약관"))
                .andExpect(jsonPath("[1]type").value("개인정보"))
                .andExpect(jsonPath("[2]type").value("마케팅"))
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme(CommonString.SCHEMA)
                                .host(CommonString.HOST),prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].terms_id").description("id").type(Long.class),
                                fieldWithPath("[].type").description("약관의 타입"),
                                fieldWithPath("[].contents").description("약관 상세 내용")
                        )
                ));
        //then
        verify(termsRepository).findAll();
    }

}
