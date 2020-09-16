package com.incense.gehasajang.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByHouse_Id(Long houseId);

    Optional<Room> findByIdAndHouse_Id(Long roomId, Long houseId);

    boolean existsByIdAndDeletedAtNullAndHouse_Id(@Param(value = "roomId") Long roomId, @Param(value = "houseId") Long houseId);

}
