package com.incense.gehasajang.domain.host;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, Long> {

    boolean existsByAccountAndDeletedAtNull(String account);

    boolean existsByNicknameAndDeletedAtNull(String nickname);

    @Query("select h.id from Host h " +
            "where exists (select hh.id from h.hostHouses hh where hh.host.id = :hostId)")
    Long findHouseByHostId(@Param(value = "hostId") Long hostId);

    @Query(nativeQuery = true,
            value = "select h.host_id from host h " +
                    "join host_house hh on h.host_id = hh.host_id " +
                    "where h.account = :account and hh.house_id = :houseId")
    Optional<Long> findHouseByAccountAndHouseId(@Param(value = "account") String account, @Param(value = "houseId") Long houseId);

    @Query(nativeQuery = true,
            value = "SELECT h.host_id FROM host h " +
                    "JOIN host_house hh ON h.host_id = hh.host_id " +
                    "JOIN house ho ON hh.house_id = ho.house_id " +
                    "JOIN room r ON ho.house_id = r.house_id " +
                    "WHERE h.host_id = :hostId")
    Long findRoomByHostId(@Param(value = "hostId") Long hostId);

    @Query("select h.isPassEmailAuth from Host h where h.id = :id")
    boolean findEmailAuthById(Long id);

    @Query("select h from Host h join fetch h.authKey k where h.account = :account and h.deletedAt is null ")
    Optional<MainHost> findMainHostByAccount(String account);

    Optional<Host> findByAccount(String account);
}
