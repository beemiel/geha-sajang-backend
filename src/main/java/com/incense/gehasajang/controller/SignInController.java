package com.incense.gehasajang.controller;

import com.incense.gehasajang.model.dto.signin.SignInRequestDto;
import com.incense.gehasajang.model.dto.signin.SignInResponseDto;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.error.ErrorResponse;
import com.incense.gehasajang.exception.*;
import com.incense.gehasajang.service.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> signIn(
            @Valid @RequestBody SignInRequestDto requestDto
    ) {
        SignInResponseDto signInResponseDto = signInService.authenticate(requestDto.getAccount(), requestDto.getPassword());
        return ResponseEntity.ok(signInResponseDto);
    }

    /**
     * 삭제된 계정일 경우
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DeletedHostException.class)
    public ErrorResponse handleDeletedHost(DeletedHostException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

    /**
     * 비활성화 계정일 경우
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DisabledHostException.class)
    public ErrorResponse handleDisabledHost(DisabledHostException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

    /**
     * 메일 인증이 완료되지 않은 경우
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthMailException.class)
    public ErrorResponse handleUnauthMail(UnAuthMailException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

}
