package com.incense.gehasajang.domain.host;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, Long> {

    boolean existsByEmailAndDeletedAtNull(String email);

    boolean existsByNicknameAndDeletedAtNull(String nickname);

    @Query("select h from Host h " +
            "join fetch h.authKey k " +
            "where h.email = :email " +
            "and h.deletedAt is null ")
    Optional<MainHost> findMainHostByEmail(String email);

}
