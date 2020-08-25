package com.incense.gehasajang.controller;

import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.domain.host.MainHost;
import com.incense.gehasajang.dto.host.EmailCheckDto;
import com.incense.gehasajang.dto.host.HostDto;
import com.incense.gehasajang.dto.host.NicknameCheckDto;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.error.ErrorResponse;
import com.incense.gehasajang.exception.*;
import com.incense.gehasajang.service.S3Service;
import com.incense.gehasajang.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<Void> join(@Valid HostDto hostDto, MultipartFile file) throws IOException {
        hostDto.setProfileImage(s3Service.upload(file, "host"));
        signUpService.addHost(toMainHost(hostDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/auth")
    public ResponseEntity<String> joinConfirm(@RequestParam String email, @RequestParam String authkey) {
        signUpService.confirm(email, authkey);
        return ResponseEntity.status(HttpStatus.CREATED).body("인증되었습니다.");
    }

    @PostMapping("/check-email")
    public ResponseEntity<Boolean> emailDuplicateCheck(@RequestBody @Valid EmailCheckDto email) {
        return ResponseEntity.ok(signUpService.checkEmail(email.getEmail()));
    }

    @PostMapping("/check-name")
    public ResponseEntity<Boolean> nameDuplicateCheck(@RequestBody @Valid NicknameCheckDto name) {
        return ResponseEntity.ok(signUpService.checkName(name.getNickname()));
    }

    private Host toMainHost(HostDto hostDto) {
        return MainHost.builder()
                .email(hostDto.getEmail())
                .nickname(hostDto.getNickname())
                .password(passwordEncoder.encode(hostDto.getPassword()))
                .profileImage(hostDto.getProfileImage())
                .address(new Address(hostDto.getCity(), hostDto.getStreet(), hostDto.getPostcode(), hostDto.getDetail()))
                .isAgreeToMarketing(hostDto.isAgreeToMarketing())
                .build();
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
                .code(ErrorCode.FAIL_TO_AUTH.getCode())
                .status(ErrorCode.FAIL_TO_AUTH.getStatus())
                .message(ErrorCode.FAIL_TO_AUTH.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DuplicateAuthException.class)
    public ErrorResponse handleDuplicateAuth (){
        return ErrorResponse.builder()
                .code(ErrorCode.DUPLICATE_AUTH.getCode())
                .status(ErrorCode.DUPLICATE_AUTH.getStatus())
                .message(ErrorCode.DUPLICATE_AUTH.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpirationException.class)
    public ErrorResponse handleExpiration (){
        return ErrorResponse.builder()
                .code(ErrorCode.EXPIRATION_AUTH.getCode())
                .status(ErrorCode.EXPIRATION_AUTH.getStatus())
                .message(ErrorCode.EXPIRATION_AUTH.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateHostException.class)
    public ErrorResponse handleDuplicateHost () {
        return ErrorResponse.builder()
                .code(ErrorCode.DUPLICATE.getCode())
                .status(ErrorCode.DUPLICATE.getStatus())
                .message(ErrorCode.DUPLICATE.getMessage())
                .build();
    }

}

