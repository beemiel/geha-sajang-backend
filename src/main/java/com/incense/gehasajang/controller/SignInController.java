package com.incense.gehasajang.controller;

import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.dto.signin.SignInRequestDto;
import com.incense.gehasajang.dto.signin.SignInResponseDto;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.error.ErrorResponse;
import com.incense.gehasajang.exception.*;
import com.incense.gehasajang.service.SignInService;
import com.incense.gehasajang.util.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto>signIn(
            @Valid @RequestBody SignInRequestDto requestDto
    ) {
        Host host = signInService.authenticate(requestDto.getAccount(), requestDto.getPassword());
        String token = jwtUtil.createToken(host.getAccount(), host.getType());

        return ResponseEntity.ok(new SignInResponseDto(token));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundDataException.class)
    public ErrorResponse handleNotFound (){
        return ErrorResponse.builder()
                .code(ErrorCode.HOST_NOT_FOUND.getCode())
                .status(ErrorCode.HOST_NOT_FOUND.getStatus())
                .message(ErrorCode.HOST_NOT_FOUND.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(FailToAuthenticationException.class)
    public ErrorResponse handleFailToAuth (){
        return ErrorResponse.builder()
                .code(ErrorCode.FAIL_TO_SIGN_IN.getCode())
                .status(ErrorCode.FAIL_TO_SIGN_IN.getStatus())
                .message(ErrorCode.FAIL_TO_SIGN_IN.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DeletedHostException.class)
    public ErrorResponse handleDeletedHost (){
        return ErrorResponse.builder()
                .code(ErrorCode.DELETED.getCode())
                .status(ErrorCode.DELETED.getStatus())
                .message(ErrorCode.DELETED.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DisabledHostException.class)
    public ErrorResponse handleDisabledHost (){
        return ErrorResponse.builder()
                .code(ErrorCode.DISABLED.getCode())
                .status(ErrorCode.DISABLED.getStatus())
                .message(ErrorCode.DISABLED.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthMailException.class)
    public ErrorResponse handleUnauthMail (){
        return ErrorResponse.builder()
                .code(ErrorCode.UNAUTH_MAIL.getCode())
                .status(ErrorCode.UNAUTH_MAIL.getStatus())
                .message(ErrorCode.UNAUTH_MAIL.getMessage())
                .build();
    }

}
