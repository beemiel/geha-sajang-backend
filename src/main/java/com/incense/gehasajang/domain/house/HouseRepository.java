package com.incense.gehasajang.domain.house;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {

    Optional<House> findById(Long houseId);

}
