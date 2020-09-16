package com.incense.gehasajang.domain.house;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {

    Optional<House> findById(@Param(value = "houseId") Long houseId);

    Optional<House> findByIdAndHostHouses_Host_Account(@Param(value = "houseId") Long houseId, @Param(value = "account") String account);

}
