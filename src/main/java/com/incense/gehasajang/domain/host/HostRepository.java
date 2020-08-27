package com.incense.gehasajang.domain.host;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, Long> {

    boolean existsByAccountAndDeletedAtNull(String account);

    boolean existsByNicknameAndDeletedAtNull(String nickname);

    @Query("select h.isPassEmailAuth from Host h where h.id = :id")
    boolean findEmailAuthById(Long id);

    @Query("select h from Host h join fetch h.authKey k where h.account = :account and h.deletedAt is null ")
    Optional<MainHost> findMainHostByAccount(String account);

    Optional<Host> findByAccount(String account);
}
