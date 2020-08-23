package com.incense.gehasajang.domain.host;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

}
