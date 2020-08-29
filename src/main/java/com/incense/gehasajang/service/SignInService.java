package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.host.HostRole;
import com.incense.gehasajang.dto.signin.SignInResponseDto;
import com.incense.gehasajang.exception.*;
import com.incense.gehasajang.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignInService {

    private final HostRepository hostRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    /**
     * 입력받은 값과 디비 값 조회 후 매칭
     * 그리고 Token을 생성하고 등록 상태를 체크한다.
     */
    public SignInResponseDto authenticate(String account, String password) {
        Host host = hostRepository.findByAccount(account)
                .orElseThrow(NotFoundDataException::new);

        if(!host.checkPassword(password, passwordEncoder)) {
            throw new FailToAuthenticationException();
        }

        checkIsDeleted(host);
        checkIsActive(host);
        checkIsPassMailAuth(host);

        String token = jwtUtil.createToken(host.getAccount(), host.getType());
        String registerState = checkRegisterState(host);

        return new SignInResponseDto(token, registerState);
    }

    //TODO: 2020-08-29 room 등록 추가 후 추가 검증 필요 -lynn
    private String checkRegisterState(Host host) {
        if(host.getType().equals(HostRole.ROLE_SUB.getType())) {
            return "staff";
        }

        if(hostRepository.findRoomByHostId(host.getId()) != null) {
            return "registered";
        }

        if(hostRepository.findHouseByHostId(host.getId()) != null) {
            return  "inProgress";
        }

        return "unregistered";
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
     * 호스트일 경우 이메일 인증 확인
     * 스태프는 확인하지 않는다.
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
