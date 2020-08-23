package com.incense.gehasajang.dto.terms;

import com.github.dozermapper.core.Mapping;
import lombok.*;

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
