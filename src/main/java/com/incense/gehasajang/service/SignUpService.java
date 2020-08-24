package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SignUpService {

    private final HostRepository hostRepository;

    public boolean checkEmail(String email) {
        return hostRepository.existsByEmail(email);
    }

    public boolean checkName(String nickname) {
        return hostRepository.existsByNickname(nickname);
    }
}
