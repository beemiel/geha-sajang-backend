package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.Stay;
import com.incense.gehasajang.domain.room.*;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class UnbookedRoomServiceTest {

    private UnbookedRoomService unbookedRoomService;

    @Mock
    private UnbookedRoomRepository unbookedRoomRepository;

    @Mock
    private BookedRoomRepository bookedRoomRepository;

    private String date1 = "2020-08-01";
    private String date2 = "2020-08-02";
    private String date3 = "2020-08-03";
    private String date4 = "2020-08-04";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unbookedRoomService = new UnbookedRoomService(unbookedRoomRepository, bookedRoomRepository);
    }

    @Test
    @DisplayName("list -> map 테스트")
    void listToMap() throws Exception {
        //given
        BookingRoomRequestDto requestDto = BookingRoomRequestDto.builder().roomId(1L).maleCount(1).femaleCount(2).build();
        Stay stay = new Stay(LocalDate.parse(date1, formatter).atStartOfDay(), LocalDate.parse(date4, formatter).atStartOfDay());
        Room room = Room.builder().id(1L).defaultCapacity(4).maxCapacity(5).memo("room memo").name("room").peakAmount("10000").offPeakAmount("13000").roomType(RoomType.DORMITORY).build();
        List<UnbookedRoom> roomList = Arrays.asList(
                UnbookedRoom.builder().id(1L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(2L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(3L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(4L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(5L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(6L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(7L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(8L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(9L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(10L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(11L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(12L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(13L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).build()
        );
        given(unbookedRoomRepository.findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(any(), any(), any())).willReturn(roomList);

        //when
        Map<LocalDateTime, List<UnbookedRoom>> rooms = unbookedRoomService.getUnbookedRoomsByRoomId(requestDto, stay);

        //then
        assertThat(rooms.size()).isEqualTo(3);
        assertThat(rooms.containsKey(LocalDate.parse(date1, formatter).atStartOfDay())).isTrue();
        assertThat(rooms.containsKey(LocalDate.parse(date2, formatter).atStartOfDay())).isTrue();
        assertThat(rooms.containsKey(LocalDate.parse(date3, formatter).atStartOfDay())).isTrue();
        assertThat(rooms.containsKey(LocalDate.parse(date4, formatter).atStartOfDay())).isFalse();
        assertThat(rooms.get(LocalDate.parse(date1, formatter).atStartOfDay()).size()).isEqualTo(3);
        assertThat(rooms.get(LocalDate.parse(date2, formatter).atStartOfDay()).size()).isEqualTo(5);
        assertThat(rooms.get(LocalDate.parse(date3, formatter).atStartOfDay()).size()).isEqualTo(5);
    }

    @Test
    @DisplayName("다인실 인원 수 확인")
    void checkAmountFromMultiple() throws Exception {
        //given
        BookingRoomRequestDto requestDto = BookingRoomRequestDto.builder().roomId(1L).maleCount(3).femaleCount(3).build();
        Stay stay = new Stay(LocalDate.parse(date1, formatter).atStartOfDay(), LocalDate.parse(date4, formatter).atStartOfDay());
        Room room = Room.builder().id(1L).defaultCapacity(2).maxCapacity(3).memo("room memo").name("room").peakAmount("10000").offPeakAmount("13000").roomType(RoomType.MULTIPLE).build();
        List<UnbookedRoom> roomList = Arrays.asList(
                UnbookedRoom.builder().id(1L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(4L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(9L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).build()
        );
        given(unbookedRoomRepository.findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(any(), any(), any())).willReturn(roomList);

        //when
        NotFoundDataException e = assertThrows(NotFoundDataException.class, () -> unbookedRoomService.getUnbookedRoomsByRoomId(requestDto, stay));

        //then
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_UNBOOKED);
    }

    @Test
    @DisplayName("도미토리 인원 수 확인")
    void checkAmountFromDormitory() throws Exception {
        //given
        BookingRoomRequestDto requestDto = BookingRoomRequestDto.builder().roomId(1L).maleCount(0).femaleCount(5).build();
        Stay stay = new Stay(LocalDate.parse(date1, formatter).atStartOfDay(), LocalDate.parse(date4, formatter).atStartOfDay());
        Room room = Room.builder().id(1L).defaultCapacity(2).maxCapacity(3).memo("room memo").name("room").peakAmount("10000").offPeakAmount("13000").roomType(RoomType.DORMITORY).build();
        List<UnbookedRoom> roomList = Arrays.asList(
                UnbookedRoom.builder().id(1L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(2L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(3L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date1, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(4L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(5L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(6L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date2, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(7L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(8L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).build(),
                UnbookedRoom.builder().id(9L).todayAmount("10000").room(room).entryDate(LocalDate.parse(date3, formatter).atStartOfDay()).build()
        );
        given(unbookedRoomRepository.findAllByEntryDateBetweenAndRoom_IdAndRoom_DeletedAtNullAndBookedRoom_UnbookedRoomNull(any(), any(), any())).willReturn(roomList);

        //when
        NotFoundDataException e = assertThrows(NotFoundDataException.class, () -> unbookedRoomService.getUnbookedRoomsByRoomId(requestDto, stay));

        //then
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_UNBOOKED);
    }

}
