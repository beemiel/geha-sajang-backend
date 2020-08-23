package com.incense.gehasajang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class SignUpController {

    //TODO: 2020-08-24 이메일 인증 -lynn

    //TODO: 2020-08-24 회원가입 post -lynn

    @PostMapping("/check-email")
    public ResponseEntity<Boolean> emailDuplicateCheck() {

        return ResponseEntity.ok(true);
    }

    @PostMapping("/check-name")
    public ResponseEntity<Boolean> nameDuplicateCheck() {

        return ResponseEntity.ok(true);
    }

}
