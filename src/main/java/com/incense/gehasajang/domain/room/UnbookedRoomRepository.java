package com.incense.gehasajang.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UnbookedRoomRepository extends JpaRepository<UnbookedRoom, Long> {

    List<UnbookedRoom> findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(@Param(value = "checkIn") LocalDateTime checkIn, @Param(value = "checkOut") LocalDateTime checkOut, @Param(value = "roomId") Long roomId);

}
