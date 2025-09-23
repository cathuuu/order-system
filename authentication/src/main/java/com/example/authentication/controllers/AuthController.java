package com.example.authentication.controllers;


import com.example.authentication.dtos.ResponseDto;
import com.example.authentication.dtos.RegisterRequestDto;
import com.example.authentication.entities.RefreshTokenEntity;
import com.example.authentication.entities.UserEntity;
import com.example.authentication.mappers.UserMapper;
import com.example.authentication.securities.JwtTokenProvider;
import com.example.authentication.services.RefreshTokenService;
import com.example.authentication.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private  UserService userService;

    private  AuthenticationManager authenticationManager;

    private UserMapper registerMapper;

    private JwtTokenProvider jwtTokenProvider;

    private RefreshTokenService refreshTokenService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto requestDto) {
        var result = userService.save(registerMapper.toEntity(requestDto));
        return ResponseEntity.ok("User registered successfully with id: " + result.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody ResponseDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        // Lấy user entity từ DB
        UserEntity user = userService.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo refresh token cho user
        RefreshTokenEntity refreshToken = refreshTokenService.createToken(
                user,
                Instant.now().plus(7, ChronoUnit.DAYS)
        );

        ResponseDto response = new ResponseDto(accessToken, refreshToken.getToken());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto> refresh(@RequestParam String refreshToken) {
        RefreshTokenEntity tokenEntity = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (tokenEntity.getExpiryDate().isBefore(Instant.now())) {
            return ResponseEntity.badRequest().build();
        }

        UserEntity user = tokenEntity.getUserId(); // vì userId là UserEntity

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                Collections.emptyList() // sau này có thể đổi thành user.getAuthorities()
        );

        String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);

        ResponseDto response = new ResponseDto(newAccessToken, refreshToken);
        return ResponseEntity.ok(response);
    }

}
