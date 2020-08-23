package com.incense.gehasajang.dto.terms;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TermsDto {

    private Long terms_id;

    private String type;

    private String contents;

    @Builder
    public TermsDto(Long terms_id, String type, String contents) {
        this.terms_id = terms_id;
        this.type = type;
        this.contents = contents;
    }
}
