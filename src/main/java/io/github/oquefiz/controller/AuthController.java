package io.github.oquefiz.controller;

import io.github.oquefiz.config.security.TokenService;
import io.github.oquefiz.dto.Request.LoginRequestDto;
import io.github.oquefiz.dto.Request.RegisterRequestDto;
import io.github.oquefiz.dto.Response.LoginResponseDto;
import io.github.oquefiz.model.User;
import io.github.oquefiz.service.AuthService;
import io.github.oquefiz.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto
            ){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.Email(), requestDto.password()
                )
        );

        UserDetails userDetails = userService.loadUserByUsername(requestDto.Email());

        String token = tokenService.generateToken((User) userService);

        return ResponseEntity.ok( new LoginResponseDto(token));

    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequestDto requestDto
            ){
        authService.register(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
