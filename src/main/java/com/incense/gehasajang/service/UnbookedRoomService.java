package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.room.*;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.param.unbooked.UnbookedListParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UnbookedRoomService {

    private final UnbookedRoomRepository unbookedRoomRepository;
    private final BookedRoomRepository bookedRoomRepository;
    private final RoomRepository roomRepository;

    //재고, 인원 수  확인
    public Map<LocalDateTime, List<UnbookedRoom>> getUnbookedRooms(UnbookedListParam unbookedListParam) {
        int amount = unbookedListParam.getCount();
        long duration = Duration.between(unbookedListParam.getCheckIn(), unbookedListParam.getCheckOut()).toDays(); //시간이 모두 00 이어야 하는걸로 알고있음
        int expectedRoomCount = (int) duration * amount;

        List<LocalDateTime> dates = getDates(unbookedListParam.getCheckIn(), duration);
        int first = 0;
        int last = dates.size() - 1;

        List<UnbookedRoom> unbookedRooms = unbookedRoomRepository.findAllUnbooked(unbookedListParam.getRoomId(), dates.get(first), dates.get(last));

        //전체 재고 개수 체크
        checkTotalCount(unbookedRooms, expectedRoomCount);

        //날짜별 리스트로 분리
        Map<LocalDateTime, List<UnbookedRoom>> stockByDate = getStockByDateFromList(dates, unbookedRooms);

        //날짜별 재고 개수 체크
        checkCountByDate(stockByDate, amount);

        return stockByDate;
    }

    private List<LocalDateTime> getDates(LocalDateTime checkIn, long duration) {
        List<LocalDateTime> dates = new ArrayList<>();
        for (int i = 0; i < duration; i++) {
            dates.add(checkIn.plusDays(i));
        }
        return dates;
    }

    private Map<LocalDateTime, List<UnbookedRoom>> getStockByDateFromList(List<LocalDateTime> dates, List<UnbookedRoom> unbookedRooms) {
        Map<LocalDateTime, List<UnbookedRoom>> stock = new HashMap<>();
        dates.forEach(date -> {
            stock.put(date, getListByDate(unbookedRooms, date));
        });
        return stock;
    }

    private List<UnbookedRoom> getListByDate(List<UnbookedRoom> unbookedRooms, LocalDateTime date) {
        List<UnbookedRoom> newRooms = new ArrayList<>();
        unbookedRooms.forEach(room -> {
            if (room.getEntryDate().equals(date)) {
                newRooms.add(room);
            }
        });
        return newRooms;
    }

    private void checkTotalCount(List<UnbookedRoom> unbookedRooms, int expectedRoomCount) {
        if (unbookedRooms.isEmpty() || unbookedRooms.size() < expectedRoomCount) {
            throw new NotFoundDataException(ErrorCode.NOT_FOUND_UNBOOKED);
        }
    }

    private void checkCountByDate(Map<LocalDateTime, List<UnbookedRoom>> stockByDate, int amount) {
        stockByDate.values().forEach((value) -> {
            if (value.size() < amount) {
                throw new NotFoundDataException(ErrorCode.NOT_FOUND_UNBOOKED);
            }
        });
    }

    public void addBookedRoom(UnbookedRoom unbookedRoom) {
        BookedRoom bookedRoom = BookedRoom.builder().unbookedRoom(unbookedRoom).build();
        bookedRoomRepository.save(bookedRoom);
    }
}
