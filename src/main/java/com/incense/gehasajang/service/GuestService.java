package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.guest.GuestRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.dto.guest.response.GuestCheckResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    //이름이 동일한 게스트들을 모두 반환
    public List<GuestCheckResponseDto> findGuests(Long houseId, String guestName) {
        Set<Guest> guests = guestRepository.findAllByNameAndBookings_House_Id(guestName, houseId);

        if (guests.isEmpty()) {
            throw new NotFoundDataException(ErrorCode.NOT_FOUND_GUEST);
        }

        return toDto(guests, houseId);
    }

    //게스트 id가 null이면 insert null이 아니면 update
    @Transactional
    public Guest addGuest(Guest guestInformation) {
        if (guestInformation.getId() != null) {
            Guest updatedGuest = updateGuest(guestInformation);
            return guestRepository.save(updatedGuest);
        }
        return guestRepository.save(guestInformation);
    }

    private Guest updateGuest(Guest guestInformation) {
        Guest guest = findGuest(guestInformation.getId());
        return guest.changeByInfo(guestInformation);
    }

    private Guest findGuest(Long guestId) {
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundDataException(ErrorCode.NOT_FOUND_GUEST));
    }

    private List<GuestCheckResponseDto> toDto(Set<Guest> guests, Long houseId) {
        return guests.stream()
                .map(guest ->
                        GuestCheckResponseDto.builder()
                                .guestId(guest.getId())
                                .name(guest.getName())
                                .phoneNumber(guest.getPhoneNumber())
                                .email(guest.getEmail())
                                .memo(guest.getMemo())
                                .lastBooking(guestRepository.findLastBookingById(guest.getId(), houseId))
                                .build())
                .collect(Collectors.toList());
    }
}
