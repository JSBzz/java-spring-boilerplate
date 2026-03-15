package com.jsb.boilerplate.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public String getTestMessage() {
        return "테스트 메서드";
    }
}
