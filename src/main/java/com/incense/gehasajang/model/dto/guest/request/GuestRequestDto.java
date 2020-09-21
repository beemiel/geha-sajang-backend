package com.incense.gehasajang.model.dto.guest.request;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestRequestDto {

    @Mapping("id")
    private Long guestId;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("phoneNumber")
    private String phoneNumber;

    @Mapping("email")
    private String email = "";

    @Size(max = 500)
    @Mapping("memo")
    private String memo = "";

}
