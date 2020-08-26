package com.incense.gehasajang.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RoomDto {

    @Mapping("id")
    private Long roomId;

    @Mapping("name")
    private String name;

    private Long houseId;

    @Mapping("memo")
    private String memo;

    @Mapping("maxCapacity")
    private int maxCapacity;

    @Mapping("defaultCapacity")
    private int defaultCapacity;

    @Mapping("peakAmount")
    private String peakAmount;

    @Mapping("offPeakAmount")
    private String offPeakAmount;

    @Builder
    public RoomDto(Long roomId, Long houseId, String name, String memo, int maxCapacity, int defaultCapacity, String peakAmount, String offPeakAmount) {
        this.roomId = roomId;
        this.houseId = houseId;
        this.name = name;
        this.memo = memo;
        this.maxCapacity = maxCapacity;
        this.defaultCapacity = defaultCapacity;
        this.peakAmount = peakAmount;
        this.offPeakAmount = offPeakAmount;
    }

}
