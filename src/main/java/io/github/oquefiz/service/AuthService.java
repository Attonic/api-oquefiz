package io.github.oquefiz.service;

import io.github.oquefiz.dto.Request.ChangePasswordRequestDto;
import io.github.oquefiz.dto.Request.LoginRequestDto;
import io.github.oquefiz.dto.Request.RegisterRequestDto;
import io.github.oquefiz.dto.Response.AuthResponse;
import io.github.oquefiz.dto.Response.UserResponseDto;

public interface AuthService {

    AuthResponse login(LoginRequestDto requestDto);
    UserResponseDto register(RegisterRequestDto requestDto);
    void changePassword(String email, ChangePasswordRequestDto requestDto);





}
