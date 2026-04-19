package io.github.oquefiz.service.impl;

import io.github.exception.ConflictException;
import io.github.exception.NotFoundException;
import io.github.oquefiz.config.security.TokenService;
import io.github.oquefiz.dto.Request.ChangePasswordRequestDto;
import io.github.oquefiz.dto.Request.LoginRequestDto;
import io.github.oquefiz.dto.Request.RegisterRequestDto;
import io.github.oquefiz.dto.Response.AuthResponse;
import io.github.oquefiz.dto.Response.UserResponseDto;
import io.github.oquefiz.model.User;
import io.github.oquefiz.model.enums.UserRole;
import io.github.oquefiz.repository.UserRepository;
import io.github.oquefiz.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public AuthResponse login(LoginRequestDto requestDto) {
        var authToken = new UsernamePasswordAuthenticationToken(
                requestDto.Email(),
                requestDto.password());

        var authentication = authenticationManager.authenticate(authToken);

        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);

        UserResponseDto userDto = UserResponseDto.fromEntity(user);

        return new AuthResponse(token, userDto);
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.email())){
            throw new ConflictException("Email já existente.");
        }
        User user = User.builder()
                .userName(requestDto.userName())
                .email(requestDto.email())
                .password(passwordEncoder.encode((requestDto.password())))
                .role(UserRole.valueOf(requestDto.role().toUpperCase()))
                .build();

        userRepository.save(user);
        String token = tokenService.generateToken(user);

        UserResponseDto userResponse = UserResponseDto.fromEntity(user);

        return new AuthResponse(token, userResponse);
    }

    @Override
    public void changePassword(String email, ChangePasswordRequestDto requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email não encontrado."));

        if(!passwordEncoder.matches(requestDto.currentPassword(), user.getPassword())){
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        if (!requestDto.newPassword().equals(requestDto.confirmPassword())){
            throw new IllegalArgumentException("Senhas não conferem.");
        }

        user.setPassword(passwordEncoder.encode(requestDto.newPassword()));
        userRepository.save(user);
    }
}
