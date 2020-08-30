package com.incense.gehasajang.controller;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.host.MainHost;
import com.incense.gehasajang.model.dto.host.EmailCheckDto;
import com.incense.gehasajang.model.dto.host.HostDto;
import com.incense.gehasajang.model.dto.host.NicknameCheckDto;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.error.ErrorResponse;
import com.incense.gehasajang.exception.*;
import com.incense.gehasajang.service.S3Service;
import com.incense.gehasajang.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    private final S3Service s3Service;

    private final Mapper mapper;

    @PostMapping()
    public ResponseEntity<Void> join(@Valid HostDto hostDto, MultipartFile image) throws IOException {
        hostDto.setProfileImage(s3Service.upload(image, "host"));
        MainHost host = mapper.map(hostDto, MainHost.class);
        signUpService.addHost(host);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/auth")
    public ResponseEntity<String> joinConfirm(@RequestParam String email, @RequestParam String authkey) {
        signUpService.confirm(email, authkey);
        return ResponseEntity.status(HttpStatus.CREATED).body("인증되었습니다.");
    }

    @PostMapping("/check-email")
    public ResponseEntity<Boolean> emailDuplicateCheck(@RequestBody @Valid EmailCheckDto email) {
        return ResponseEntity.ok(signUpService.checkAccount(email.getEmail()));
    }

    @PostMapping("/check-name")
    public ResponseEntity<Boolean> nameDuplicateCheck(@RequestBody @Valid NicknameCheckDto name) {
        return ResponseEntity.ok(signUpService.checkName(name.getNickname()));
    }

    /**
     * 중복 인증 요청
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DuplicateAuthException.class)
    public ErrorResponse handleDuplicateAuth(DuplicateAuthException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

    /**
     * 만료된 인증키
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpirationException.class)
    public ErrorResponse handleExpiration(ExpirationException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

    /**
     * 이미 등록된 호스트
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateHostException.class)
    public ErrorResponse handleDuplicateHost(DuplicateHostException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

    /**
     * 메일 전송 실패
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CannotSendMailException.class)
    public ErrorResponse handleCannotSendMailException(CannotSendMailException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

}

