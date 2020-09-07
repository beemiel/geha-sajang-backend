package com.incense.gehasajang.controller;

import com.incense.gehasajang.model.dto.guest.response.GuestCheckResponseDto;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.AuthorizationService;
import com.incense.gehasajang.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/houses/{houseId}")
public class GuestController {

    private final AuthorizationService authorizationService;
    private final GuestService guestService;

    @GetMapping("/guests")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_MAIN')")
    public ResponseEntity<List<GuestCheckResponseDto>> list(
            @PathVariable @Min(value = 1) Long houseId,
            @RequestParam String name,
            @AuthenticationPrincipal UserAuthentication authentication
            ) {
        authorizationService.checkHouse(houseId, authentication.getAccount());
        return ResponseEntity.ok(guestService.findGuests(houseId, name));
    }

}
