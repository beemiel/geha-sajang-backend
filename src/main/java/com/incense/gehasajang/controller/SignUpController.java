package com.incense.gehasajang.controller;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.domain.host.MainHost;
import com.incense.gehasajang.dto.host.EmailCheckDto;
import com.incense.gehasajang.dto.host.HostDto;
import com.incense.gehasajang.dto.host.NicknameCheckDto;
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
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    private final S3Service s3Service;

    //TODO: 2020-08-24 이메일 인증 -lynn

    @PostMapping()
    public ResponseEntity<Void> join(@Valid HostDto hostDto, MultipartFile file) throws IOException {
        hostDto.setProfileImage(s3Service.upload(file, "host"));
        signUpService.addHost(toMainHost(hostDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
                .password(hostDto.getPassword())
                .profileImage(hostDto.getProfileImage())
                .address(new Address(hostDto.getCity(), hostDto.getStreet(), hostDto.getPostcode(), hostDto.getDetail()))
                .isAgreeToMarketing(hostDto.isAgreeToMarketing())
                .build();
    }

}

