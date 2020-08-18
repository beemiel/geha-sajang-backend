package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.HouseRepository;
import com.incense.gehasajang.domain.house.House;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class HouseServiceTest {

    private HouseService houseService;

    @Mock
    private HouseRepository houseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        houseService = new HouseService(houseRepository);
    }

    @Test
    @DisplayName("하우스_정보_가져오기")
    void getHouse() {
        //given
        House returnHouse = House.builder()
                .name("게스트하우스")
                .address(new Address("city", "street", "postcode", "detail"))
                .build();
        given(houseRepository.findById(1L)).willReturn(Optional.of(returnHouse));

        //when
        House house = houseService.getHouse(1L);

        //then
        assertThat(house.getName()).isEqualTo("게스트하우스");
        verify(houseRepository).findById(1L);
    }
}
