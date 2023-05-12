package com.example.cash.service.impl;

import com.example.cash.service.AuthService;
import com.example.cash.web.dto.auth.JwtRequest;
import com.example.cash.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
