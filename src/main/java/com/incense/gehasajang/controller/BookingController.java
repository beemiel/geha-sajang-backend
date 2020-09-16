package com.incense.gehasajang.controller;

import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.error.ErrorResponse;
import com.incense.gehasajang.exception.ZeroCountException;
import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.BookingService;
import com.incense.gehasajang.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/houses/{houseId}/bookings")
public class BookingController {

    private final HouseService houseService;
    private final BookingService bookingService;

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

    /**
     * 인원수가 0인 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ZeroCountException.class)
    public ErrorResponse handleZeroCount(ZeroCountException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

}
