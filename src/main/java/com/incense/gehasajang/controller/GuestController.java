package com.incense.gehasajang.controller;

import com.incense.gehasajang.model.dto.guest.response.GuestCheckResponseDto;
import com.incense.gehasajang.model.param.room.GuestListRequestParam;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/houses/{houseId}")
public class GuestController {

    private final GuestService guestService;

    @GetMapping("/guests")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_MAIN')")
    public ResponseEntity<List<GuestCheckResponseDto>> list(
            @PathVariable Long houseId,
            @RequestParam String name,
            @AuthenticationPrincipal UserAuthentication authentication
            ) {
        GuestListRequestParam param = GuestListRequestParam.builder().houseId(houseId).guestName(name).hostAccount(authentication.getAccount()).build();
        return ResponseEntity.ok(guestService.findGuest(param));
    }

}
