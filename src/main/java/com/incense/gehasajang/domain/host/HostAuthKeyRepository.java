package com.incense.gehasajang.domain.host;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HostAuthKeyRepository extends JpaRepository<HostAuthKey, Long> {

    Optional<HostAuthKey> findByAuthKey(@Param(value = "authKey") String authKey);

}
