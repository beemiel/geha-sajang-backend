package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.house.*;
import com.incense.gehasajang.exception.AccessDeniedException;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.exception.NumberExceededException;
import com.incense.gehasajang.util.CommonString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseService {

    private final HouseRepository houseRepository;
    private final HostRepository hostRepository;
    private final HouseExtraInfoRepository houseExtraInfoRepository;
    private final HostHouseRepository hostHouseRepository;

    public House getHouse(Long houseId, String account) {
        authorityCheck(houseId, account);
        return houseRepository.findById(houseId).orElseThrow(NotFoundDataException::new);
    }

    @Transactional
    public void addHouse(House house, String extra, String account) {
        House saveHouse = houseRepository.save(house);

        addHostHouse(saveHouse, account);

        if(extra==null || extra.isEmpty()) {
            return;
        }

        addHouseExtraInfo(extra, saveHouse);
    }

    private void addHostHouse(House savedHouse, String account) {
        Host host = hostRepository.findByAccount(account).orElseThrow(NotFoundDataException::new);
        HostHouse hostHouse = HostHouse.builder().host(host).house(savedHouse).build();
        hostHouseRepository.save(hostHouse);
    }

    //TODO: 2020-08-24 cascade 설정 추가한 뒤 아래 메서드 수정 or 삭제 -lynn
    private void addHouseExtraInfo(String extra, House saveHouse) {
        String[] extraInfos = extra.split(CommonString.SEPORATOR);
        checkExtraLength(extraInfos);

        Set<String> extras = Arrays.stream(extraInfos).collect(Collectors.toSet());
        extras.stream()
                .map(info -> HouseExtraInfo.builder().title(info).house(saveHouse).build())
                .forEach(houseExtraInfoRepository::save);
    }

    private void checkExtraLength(String[] extraInfos){
        if(extraInfos.length > 16) {
            throw new NumberExceededException();
        }
    }

    private void authorityCheck(Long houseId, String account) {
        hostRepository.findHouseByAccountAndHouseId(account, houseId).orElseThrow(AccessDeniedException::new);
    }

}

