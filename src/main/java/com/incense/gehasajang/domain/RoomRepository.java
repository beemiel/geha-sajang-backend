package com.incense.gehasajang.domain;

import com.incense.gehasajang.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Transactional
    @Modifying
    @Query(value = "SELECT r FROM Room r WHERE r.house.id = :houseId")
    List<Room> findAllByHouseEquals(@Param("houseId") Long houseId);
}
