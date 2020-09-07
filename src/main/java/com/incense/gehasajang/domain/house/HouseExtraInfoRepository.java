package com.incense.gehasajang.domain.house;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HouseExtraInfoRepository extends JpaRepository<HouseExtraInfo, Long> {

    Optional<HouseExtraInfo> findByIdAndHouse_Id(@Param(value = "id") Long id, @Param(value = "houseId") Long houseId);

}
