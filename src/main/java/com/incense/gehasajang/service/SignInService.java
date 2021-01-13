package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.FailToAuthenticationException;
import com.incense.gehasajang.model.dto.signin.SignInResponseDto;
import com.incense.gehasajang.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignInService {

    private final HostService hostService;

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 입력받은 값과 디비 값 조회 후 매칭
     * 그리고 Token을 생성하고 등록 상태를 체크한다.
     */
    public SignInResponseDto authenticate(String account, String password) {
        Host host = hostService.findHost(account);

        if (!host.checkPassword(password, passwordEncoder)) {
            throw new FailToAuthenticationException(ErrorCode.FAIL_TO_SIGN_IN);
        }

        checkHostState(host);

        return SignInResponseDto.builder()
                .accessToken(jwtUtil.createToken(host.getAccount(), host.getType()))
                .registerState(hostService.checkRegisterState(host))
                .nickname(host.getNickname())
                .profileImage(host.getProfileImage())
                .build();
    }

    private void checkHostState(Host host) {
        hostService.checkIsDeleted(host);
        hostService.checkIsActive(host);
        hostService.checkIsPassMailAuth(host);
    }

}
