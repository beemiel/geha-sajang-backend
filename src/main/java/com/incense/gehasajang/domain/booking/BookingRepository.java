package com.incense.gehasajang.domain.booking;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @EntityGraph(attributePaths = {"guest", "bookingExtraInfos.houseExtraInfo", "bookingRoomInfos.unbookedRoom.room"})
    @Query("select b from Booking b where b.id = :bookingId and b.deletedAt is null")
    Optional<Booking> findBooking(@Param(value = "bookingId") Long bookingId);

    boolean existsByIdAndDeletedAtNullAndHouse_IdAndHouse_HostHouses_Host_Account(@Param(value = "houseId")Long houseId, @Param(value = "bookingId")Long bookingId, @Param(value = "account")String account);

}
