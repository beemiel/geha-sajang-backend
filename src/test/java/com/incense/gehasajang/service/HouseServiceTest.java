package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.host.MainHost;
import com.incense.gehasajang.domain.house.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class HouseServiceTest {

    private HouseService houseService;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private HouseExtraInfoRepository houseExtraInfoRepository;

    @Mock
    private HostHouseRepository hostHouseRepository;

    @Mock
    private HostRepository hostRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        houseService = new HouseService(houseRepository, hostRepository, houseExtraInfoRepository, hostHouseRepository);
    }

    @Test
    @DisplayName("하우스_정보_가져오기")
    void getHouse() {
        //given
        House returnHouse = House.builder()
                .name("게스트하우스")
                .address(new Address("city", "street", "postcode", "detail"))
                .build();
        MainHost host = MainHost.builder().build();
        given(hostRepository.findByAccountAndHostHouses_House_Id(any(), any())).willReturn(Optional.ofNullable(host));
        given(houseRepository.findById(1L)).willReturn(Optional.of(returnHouse));

        //when
        House house = houseService.getHouse(1L, "account");

        //then
        assertThat(house.getName()).isEqualTo("게스트하우스");
        verify(houseRepository).findById(1L);
    }

    @Test
    @DisplayName("하우스 추가")
    public void addHouse() {
        //given
        String extra = "조식☆§♥♨☎픽업☆§♥♨☎저녁☆§♥♨☎장비대여";
        House house = House.builder().name("게스트하우스")
                .address(new Address("시티", "스트릿", "우편번호", "상세주소"))
                .mainNumber("01012345678")
                .mainImage("메인 이미지")
                .build();
        given(hostRepository.findByAccount(any())).willReturn(Optional.ofNullable(MainHost.builder().build()));

        //when
        houseService.addHouse(house, extra, "account");

        //then
        verify(houseRepository).save(house);
        verify(houseExtraInfoRepository, times(4)).save(any(HouseExtraInfo.class));
    }

    @Test
    @DisplayName("하우스 추가 정보 중복 처리 후 추가")
    public void addDuplicatedHouseExtra() {
        //given
        String extra = "조식☆§♥♨☎픽업☆§♥♨☎저녁☆§♥♨☎저녁";
        House house = House.builder().name("게스트하우스")
                .address(new Address("시티", "스트릿", "우편번호", "상세주소"))
                .mainNumber("01012345678")
                .mainImage("메인 이미지")
                .build();
        given(hostRepository.findByAccount(any())).willReturn(Optional.ofNullable(MainHost.builder().build()));

        //when
        houseService.addHouse(house, extra, "account");

        //then
        verify(houseRepository).save(house);
        verify(houseExtraInfoRepository, times(3)).save(any(HouseExtraInfo.class));
    }

}
