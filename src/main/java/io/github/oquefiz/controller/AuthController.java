package io.github.oquefiz.controller;

import io.github.oquefiz.dto.Request.LoginRequestDto;
import io.github.oquefiz.dto.Request.RegisterRequestDto;
import io.github.oquefiz.dto.Response.AuthResponse;
import io.github.oquefiz.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequestDto requestDto
            ){
        AuthResponse authResponse = authService.login(requestDto);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequestDto requestDto
            ){
        AuthResponse authResponse = authService.register(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

}
