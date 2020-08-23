package com.incense.gehasajang.controller;

import com.incense.gehasajang.dto.host.EmailCheckDto;
import com.incense.gehasajang.dto.host.NameCheckDto;
import com.incense.gehasajang.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    //TODO: 2020-08-24 이메일 인증 -lynn

    //TODO: 2020-08-24 회원가입 post -lynn

    @PostMapping("/check-email")
    public ResponseEntity<Boolean> emailDuplicateCheck(@RequestBody @Valid EmailCheckDto email) {
        return ResponseEntity.ok(signUpService.checkEmail(email.getEmail()));
    }

    @PostMapping("/check-name")
    public ResponseEntity<Boolean> nameDuplicateCheck(@RequestBody @Valid NameCheckDto name) {
        return ResponseEntity.ok(signUpService.checkName(name.getName()));
    }

}
