package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.host.HostRole;
import com.incense.gehasajang.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {

    private final HostRepository hostRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 입력받은 값과 디비 값 조회 후 매칭
     */
    public Host authenticate(String account, String password) {
        Host host = hostRepository.findByAccount(account)
                .orElseThrow(NotFoundDataException::new);

        if(!host.checkPassword(password, passwordEncoder)) {
            throw new FailToAuthenticationException();
        }

        checkIsDeleted(host);
        checkIsActive(host);
        checkIsPassMailAuth(host);

        return host;
    }

    /**
     * 계정 활성화 여부 확인
     */
    private void checkIsActive(Host host) {
        if(!host.isActive()) {
            throw new DisabledHostException();
        }
    }

    /**
     * 계정 삭제 여부 확인
     */
    private void checkIsDeleted(Host host) {
        if(host.getDeletedAt() != null) {
            throw new DeletedHostException();
        }
    }

    /**
     * 이메일 인증 확인
     */
    private void checkIsPassMailAuth(Host host) {
        if(host.getType().equals(HostRole.ROLE_MAIN.getType())) {
            boolean isPassEmailAuth = hostRepository.findEmailAuthById(host.getId());

            if(!isPassEmailAuth) {
                throw new UnAuthMailException();
            }
        }
    }

}
