package com.incense.gehasajang.domain.house;

import com.incense.gehasajang.domain.booking.BookingExtraInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HouseExtraInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_extra_info_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @OneToMany(mappedBy = "houseExtraInfo")
    private List<BookingExtraInfo> bookingExtraInfos;

    private String title;

    private LocalDateTime deletedAt;

    @Builder
    public HouseExtraInfo(House house, String title) {
        this.house = house;
        this.title = title;
    }


}
