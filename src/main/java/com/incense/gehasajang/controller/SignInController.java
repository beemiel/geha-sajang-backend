package com.incense.gehasajang.controller;

import com.incense.gehasajang.error.ErrorResponse;
import com.incense.gehasajang.exception.DeletedHostException;
import com.incense.gehasajang.exception.DisabledHostException;
import com.incense.gehasajang.exception.UnAuthMailException;
import com.incense.gehasajang.model.dto.signin.SignInRequestDto;
import com.incense.gehasajang.model.dto.signin.SignInResponseDto;
import com.incense.gehasajang.service.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.incense.gehasajang.util.CommonString.TOKEN_COOKIE_NAME;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> signIn(
            @Valid @RequestBody SignInRequestDto requestDto,
            HttpServletResponse response
    ) {
        SignInResponseDto signInResponseDto = signInService.authenticate(requestDto.getAccount(), requestDto.getPassword());
        response.addCookie(createCookie(signInResponseDto.getAccessToken()));
        return ResponseEntity.ok(signInResponseDto);
    }

    private Cookie createCookie(String accessToken) {
        Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, accessToken);
        cookie.setPath("/");
        return cookie;
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
