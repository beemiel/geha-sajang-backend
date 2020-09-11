package com.incense.gehasajang.model.dto.room;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    @Mapping("id")
    private Long roomId;

    @NotNull(message = "방 이름을 입력해주세요.")
    @Size(max = 25, message = "25자 이내로 작성해주세요.")
    @Mapping("name")
    private String name;

    private Long houseId;

    @Mapping("memo")
    private String memo;

    @NotBlank(message = "방 타입을 입력해주세요.")
    private String roomTypeName;

    @Mapping("maxCapacity")
    private int maxCapacity;

    @Mapping("defaultCapacity")
    private int defaultCapacity;

    @NotBlank(message = "성수기 가격을 입력해주세요.")
    @Mapping("peakAmount")
    private String peakAmount;

    @NotBlank(message = "비성수기 가격을 입력해주세요.")
    @Mapping("offPeakAmount")
    private String offPeakAmount;

}
