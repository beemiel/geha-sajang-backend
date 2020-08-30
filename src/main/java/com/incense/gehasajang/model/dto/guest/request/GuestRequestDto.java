package com.incense.gehasajang.model.dto.guest.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

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

    private String memo;

}
