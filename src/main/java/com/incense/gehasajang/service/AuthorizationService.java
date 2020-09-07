package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorizationService {

    private final HostRepository hostRepository;
    private final HouseRepository houseRepository;

    public void checkHouse(Long houseId, String account) {
        hostRepository.findByAccountAndHostHouses_House_Id(account, houseId)
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED));
    }

    public boolean checkRoom(Long roomId, Long houseId) {
        return houseRepository.existsByIdAndRooms_Id(houseId, roomId);
    }
}
