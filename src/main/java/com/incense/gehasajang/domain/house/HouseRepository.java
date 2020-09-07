package com.incense.gehasajang.domain.house;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {

    Optional<House> findById(Long houseId);

    boolean existsByIdAndRooms_Id(@Param(value = "houseId") Long houseId, @Param(value = "roomId") Long roomId);

}
