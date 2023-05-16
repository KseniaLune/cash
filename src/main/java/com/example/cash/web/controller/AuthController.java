package com.example.cash.web.controller;

import com.example.cash.domain.user.User;
import com.example.cash.service.AuthService;
import com.example.cash.service.UserService;
import com.example.cash.web.dto.auth.JwtRequest;
import com.example.cash.web.dto.auth.JwtResponse;
import com.example.cash.web.dto.user.UserDto;
import com.example.cash.web.dto.validation.OnCreate;
import com.example.cash.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("ok, it wooooorks!");
    }

    @PostMapping("/login")
    public JwtResponse login (@Validated @RequestBody JwtRequest jwtRequest){
        return authService.login(jwtRequest);
    }

    @PostMapping("/register")
    public UserDto register (@Validated(OnCreate.class) @RequestBody UserDto dto){
        User createdUser = userService.create(userMapper.toEntity(dto));
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh (@RequestBody String refreshToken){
       return authService.refresh(refreshToken);
    }

}
