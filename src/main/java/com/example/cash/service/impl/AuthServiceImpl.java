package com.example.cash.service.impl;

import com.example.cash.domain.exception.ResourceNotFoundEx;
import com.example.cash.domain.user.User;
import com.example.cash.service.AuthService;
import com.example.cash.service.UserService;
import com.example.cash.web.dto.auth.JwtRequest;
import com.example.cash.web.dto.auth.JwtResponse;
import com.example.cash.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        User user = userService.getByUsername(loginRequest.getUsername());

        String accessToken = tokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles());
        String refreshToken = tokenProvider.createRefreshToken(user.getId(), user.getUsername());

        JwtResponse jwtResponse = JwtResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return tokenProvider.refreshUserTokens(refreshToken);
    }
}
