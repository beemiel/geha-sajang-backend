package com.incense.gehasajang.model.dto.terms;

import com.github.dozermapper.core.Mapping;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsDto {

    @Mapping("id")
    private Long terms_id;

    @Mapping("type")
    private String type;

    @Mapping("contents")
    private String contents;

}
