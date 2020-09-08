package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.host.MainHost;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.DeletedHostException;
import com.incense.gehasajang.exception.DisabledHostException;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.exception.UnAuthMailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostService {

    private final HostRepository hostRepository;

    public Host findHost(String account) {
        return hostRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundDataException(ErrorCode.HOST_NOT_FOUND));
    }

    public MainHost findMainHost(String account) {
        return hostRepository.findMainHostByAccount(account)
                .orElseThrow(() -> new NotFoundDataException(ErrorCode.HOST_NOT_FOUND));
    }

    //TODO: 2020-08-30 outer join으로 변경하면 IO를 한번으로 줄일 수 있을듯 -lynn
    //TODO: 2020-09-09 도메인에서 해결할 순 없을까? -lynn
    public String checkRegisterState(Host host) {
        if (host.isSubHost()) {
            return "staff";
        }

        if (hostRepository.findRoomByHostId(host.getId()) != null) {
            return "registered";
        }

        if (hostRepository.findHouseByHostId(host.getId()) != null) {
            return "inProgress";
        }

        return "unregistered";
    }

    /**
     * 계정 활성화 여부 확인
     */
    public void checkIsActive(Host host) {
        if (!host.isActive()) {
            throw new DisabledHostException(ErrorCode.DISABLED);
        }
    }

    /**
     * 계정 삭제 여부 확인
     */
    public void checkIsDeleted(Host host) {
        if (host.isDeleted()) {
            throw new DeletedHostException(ErrorCode.DELETED);
        }
    }

    /**
     * 호스트일 경우 이메일 인증 확인
     * 스태프는 확인하지 않는다.
     */
    public void checkIsPassMailAuth(Host host) {
        if (!host.isSubHost()) {
            boolean isPassEmailAuth = hostRepository.findEmailAuthById(host.getId());
            if (!isPassEmailAuth) {
                throw new UnAuthMailException(ErrorCode.UNAUTH_MAIL);
            }
        }
    }

}
