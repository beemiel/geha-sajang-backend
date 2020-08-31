package com.incense.gehasajang.model.dto.guest.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    private String email;

    @Size(max = 500)
    private String memo;

}
