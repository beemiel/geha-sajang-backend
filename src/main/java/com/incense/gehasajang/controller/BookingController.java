package com.incense.gehasajang.controller;

import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.AuthorizationService;
import com.incense.gehasajang.service.BookingService;
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

    private final AuthorizationService authorizationService;
    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_MAIN')")
    public ResponseEntity<Void> create(
            @RequestBody @Valid BookingRequestDto request,
            @PathVariable @Min(value = 1) Long houseId,
            @AuthenticationPrincipal UserAuthentication authentication
    ) {
        authorizationService.checkHouse(houseId, authentication.getAccount());
        bookingService.addBookingInfo(request, houseId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
