package com.incense.gehasajang.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class RoomDto {

    private Long roomId;

    private String name;

    private String memo;

    private int maxCapacity;

    private int defaultCapacity;

    private String peakAmount;

    private String offPeakAmount;

    @Builder
    public RoomDto(Long roomId, String name, String memo, int maxCapacity, int defaultCapacity, String peakAmount, String offPeakAmount) {
        this.roomId = roomId;
        this.name = name;
        this.memo = memo;
        this.maxCapacity = maxCapacity;
        this.defaultCapacity = defaultCapacity;
        this.peakAmount = peakAmount;
        this.offPeakAmount = offPeakAmount;
    }


}
