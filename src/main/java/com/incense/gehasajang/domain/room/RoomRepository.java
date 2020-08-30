package com.incense.gehasajang.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByHouse_Id(Long houseId);

    Optional<Room> findByIdAndHouse_Id(Long roomId, Long houseId);
}
