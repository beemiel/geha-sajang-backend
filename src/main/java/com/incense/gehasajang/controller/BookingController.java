package com.incense.gehasajang.controller;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.booking.BookingExtraInfo;
import com.incense.gehasajang.domain.booking.BookingRoomInfo;
import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.error.ErrorResponse;
import com.incense.gehasajang.exception.ZeroCountException;
import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import com.incense.gehasajang.model.dto.booking.response.BookingExtraResponseDto;
import com.incense.gehasajang.model.dto.booking.response.BookingResponseDto;
import com.incense.gehasajang.model.dto.booking.response.BookingRoomInfoResponseDto;
import com.incense.gehasajang.model.dto.guest.response.GuestResponseDto;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.AuthorizationService;
import com.incense.gehasajang.service.BookingService;
import com.incense.gehasajang.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/houses/{houseId}/bookings")
public class BookingController {

    private final HouseService houseService;
    private final BookingService bookingService;
    private final AuthorizationService authorizationService;

    private final Mapper mapper;

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> detail(
            @PathVariable @Min(value = 1) Long houseId,
            @PathVariable @Min(value = 1) Long bookingId,
            @AuthenticationPrincipal UserAuthentication authentication
    ) {
        authorizationService.isExistsBooking(bookingId, authentication.getAccount());
        return ResponseEntity.ok(convertToBookingResponseDto(bookingService.getBooking(bookingId)));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_MAIN')")
    public ResponseEntity<Void> create(
            @RequestBody @Valid BookingRequestDto request,
            @PathVariable @Min(value = 1) Long houseId,
            @AuthenticationPrincipal UserAuthentication authentication
    ) {
        House house = houseService.getHouse(houseId, authentication.getAccount());
        bookingService.addBookingInfo(request, house);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private BookingResponseDto convertToBookingResponseDto(Booking booking) {
        BookingResponseDto bookingResponseDto = mapper.map(booking, BookingResponseDto.class);
        Guest guest = (Guest) unproxy(booking.getGuest());

        bookingResponseDto.addGuest(mapper.map(guest, GuestResponseDto.class));
        bookingResponseDto.addBookingExtraResponseDto(convertToBookingExtraResponseDtos(booking.getBookingExtraInfos()));
        bookingResponseDto.addBookingRoomInfoResponseDto(convertToBookingRoomInfoResponseDtos(booking.getBookingRoomInfos()));
        return bookingResponseDto;
    }

    private List<BookingExtraResponseDto> convertToBookingExtraResponseDtos(Set<BookingExtraInfo> bookingExtraInfos) {
        return bookingExtraInfos.stream()
                .map(extra -> mapper.map(extra, BookingExtraResponseDto.class))
                .collect(Collectors.toList());
    }

    private List<BookingRoomInfoResponseDto> convertToBookingRoomInfoResponseDtos(Set<BookingRoomInfo> bookingRoomInfos) {
        List<BookingRoomInfoResponseDto> bookingRoomInfoResponseDtos = new ArrayList<>();
        Set<Room> rooms = getRooms(bookingRoomInfos);

        rooms.forEach(room -> {
            BookingRoomInfoResponseDto responseDto = BookingRoomInfoResponseDto.builder().roomName(room.getName()).build();

            bookingRoomInfos.stream()
                    .filter(info -> info.getUnbookedRoom().getRoom() == room)
                    .forEach(info -> responseDto.addCount(info.getGender()));

            bookingRoomInfoResponseDtos.add(responseDto);
        });

        return bookingRoomInfoResponseDtos;
    }

    private Set<Room> getRooms(Set<BookingRoomInfo> bookingRoomInfos) {
        return bookingRoomInfos.stream()
                .map(info -> info.getUnbookedRoom().getRoom())
                .collect(Collectors.toSet());
    }

    private Object unproxy(Object obj) {
        if (obj instanceof HibernateProxy) {
            return Hibernate.unproxy(obj);
        }
        return obj;
    }

    /**
     * 인원수가 0인 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ZeroCountException.class)
    public ErrorResponse handleZeroCount(ZeroCountException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

}
