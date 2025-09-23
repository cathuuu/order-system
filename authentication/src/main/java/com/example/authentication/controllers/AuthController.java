package com.example.authentication.controllers;


import com.example.authentication.dtos.RequestDto;
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
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper registerMapper;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenService refreshTokenService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, UserMapper registerMapper, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.registerMapper = registerMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto requestDto) {
        var result = userService.create(registerMapper.toEntity(requestDto));
        return ResponseEntity.ok("User registered successfully with id: " + result.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody RequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        UserEntity user = userService.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

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
