package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.Stay;
import com.incense.gehasajang.domain.room.BookedRoom;
import com.incense.gehasajang.domain.room.BookedRoomRepository;
import com.incense.gehasajang.domain.room.UnbookedRoom;
import com.incense.gehasajang.domain.room.UnbookedRoomRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.exception.ZeroCountException;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UnbookedRoomService {

    private final UnbookedRoomRepository unbookedRoomRepository;
    private final BookedRoomRepository bookedRoomRepository;

    private List<LocalDateTime> dates;

    public void addBookedRoom(UnbookedRoom unbookedRoom) {
        BookedRoom bookedRoom = BookedRoom.builder().unbookedRoom(unbookedRoom).build();
        bookedRoomRepository.save(bookedRoom);
    }

    //재고, 인원 수  확인
    @Transactional(readOnly = true)
    public Map<LocalDateTime, List<UnbookedRoom>> getUnbookedRoomsByRoomId(BookingRoomRequestDto roomRequestDto, Stay stay) {
        dates = stay.getDates();
        Long roomId = roomRequestDto.getRoomId();
        int amount = roomRequestDto.sum();
        final int FIRST_STAY = 0;
        final int LAST_STAY = dates.size() - 1;

        checkZero(amount);

        //모든 재고를 날짜별 리스트로 분리 및 재고 개수 체크
        List<UnbookedRoom> unbookedRooms = unbookedRoomRepository.findAllUnbooked(roomId, dates.get(FIRST_STAY), dates.get(LAST_STAY));
        return sortByDate(unbookedRooms, amount);
    }

    private Map<LocalDateTime, List<UnbookedRoom>> sortByDate(List<UnbookedRoom> unbookedRooms, int amount) {
        Map<LocalDateTime, List<UnbookedRoom>> stock = new HashMap<>();

        dates.forEach(date -> {
            List<UnbookedRoom> stockByDate = getListByDate(unbookedRooms, date);
            checkCount(stockByDate.size(), amount);
            stock.put(date, stockByDate);
        });

        return stock;
    }

    private List<UnbookedRoom> getListByDate(List<UnbookedRoom> unbookedRooms, LocalDateTime date) {
        return unbookedRooms.stream()
                .filter(room -> room.checkDate(date))
                .collect(Collectors.toList());
    }

    private void checkCount(int stockSize, int amount) {
        if (stockSize < amount) {
            throw new NotFoundDataException(ErrorCode.NOT_FOUND_UNBOOKED);
        }
    }

    private void checkZero(int amount) {
        final int ZERO = 0;
        if(amount <= ZERO) {
            throw new ZeroCountException(ErrorCode.ZERO_COUNT);
        }
    }

}
