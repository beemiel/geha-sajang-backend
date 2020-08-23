package com.incense.gehasajang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignUpService {

    public boolean checkEmail(String email) {
        return true;
    }

    public boolean checkName(String name) {
        return true;
    }
}
