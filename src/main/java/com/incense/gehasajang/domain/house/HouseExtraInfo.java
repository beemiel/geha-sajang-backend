package com.incense.gehasajang.domain.house;

import com.incense.gehasajang.domain.booking.BookingExtraInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HouseExtraInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_extra_info_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @OneToMany(mappedBy = "houseExtraInfo")
    private List<BookingExtraInfo> bookingExtraInfos;

    private String title;

    private LocalDateTime deletedAt;

    @Builder
    public HouseExtraInfo(Long id, House house, String title) {
        this.id = id;
        this.house = house;
        this.title = title;
    }


}
