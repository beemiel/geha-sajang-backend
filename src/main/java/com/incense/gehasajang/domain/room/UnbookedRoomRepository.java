package com.incense.gehasajang.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UnbookedRoomRepository extends JpaRepository<UnbookedRoom, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM unbooked_room unbook JOIN room r ON unbook.room_id = r.room_id " +
                    "LEFT OUTER JOIN booked_room booked ON unbook.unbooked_room_id = booked.unbooked_room_id " +
                    "WHERE unbook.room_id= :roomId AND booked.unbooked_room_id is null " +
                    "AND entry_date BETWEEN :checkIn and :checkOut")
    List<UnbookedRoom> findAllUnbooked(@Param(value = "roomId") Long roomId, @Param(value = "checkIn") LocalDateTime checkIn, @Param(value = "checkOut") LocalDateTime checkOut);

}
