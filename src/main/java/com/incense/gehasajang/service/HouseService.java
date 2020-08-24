package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseExtraInfo;
import com.incense.gehasajang.domain.house.HouseExtraInfoRepository;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.exception.NumberExceededException;
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

    private final HouseExtraInfoRepository houseExtraInfoRepository;

    public House getHouse(Long houseId) {
        return houseRepository.findById(houseId)
                .orElseThrow(NotFoundDataException::new);
    }

    @Transactional
    public void addHouse(House house, String extra) {
        House saveHouse = houseRepository.save(house);
        if(!extra.isEmpty()) {
            addHouseExtraInfo(extra, saveHouse);
        }
    }

    //TODO: 2020-08-24 cascade 설정 추가한 뒤 아래 메서드 수정 or 삭제 -lynn
    private void addHouseExtraInfo(String extra, House saveHouse) {
        String[] extraInfos = extra.split("☆§♥♨☎");
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

}

