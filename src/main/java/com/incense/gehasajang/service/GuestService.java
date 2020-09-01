package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.guest.GuestRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    //이름이 동일한 게스트들을 모두 반환
    public List<Guest> findGuest(Long houseId, String name) {
        return null;
    }

    //게스트 id가 null이면 insert null이 아니면 update
    public Guest addGuest(Guest guest, GuestRequestDto guestRequestDto) {
        if (guestRequestDto.getGuestId() != null) {
            Guest updatedGuest =  updateGuest(guestRequestDto, guestRequestDto.getGuestId());
            return guestRepository.save(updatedGuest);
        }
        return guestRepository.save(guest);
    }

    public Guest updateGuest(GuestRequestDto guestRequestDto, Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundDataException(ErrorCode.NOT_FOUND_GUEST));
        return guest.changeByInfo(guestRequestDto);
    }
}
