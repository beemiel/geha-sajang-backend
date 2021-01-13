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

    @Query("select b " +
            "from Booking b " +
            "left outer join House h on b.house.id = h.id " +
            "left outer join HostHouse hh on h.id = hh.house.id " +
            "left outer join Host ho on hh.host.id = ho.id " +
            "where ho.account = :account " +
            "and b.deletedAt is null " +
            "and b.id = :bookingId")
    Booking existsBooking(@Param(value = "bookingId")Long bookingId, @Param(value = "account")String account);

}
