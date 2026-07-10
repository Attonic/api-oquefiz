package io.github.oquefiz.controller;

import io.github.oquefiz.dto.Request.LoginRequestDto;
import io.github.oquefiz.dto.Request.RegisterRequestDto;
import io.github.oquefiz.dto.Response.AuthResponse;
import io.github.oquefiz.exception.ConflictException;
import io.github.oquefiz.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "Contém todas as operações responsáveis pelo login e registro de usuários.")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "Faz o login do usuário ao sistema")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequestDto requestDto
            ){
        AuthResponse authResponse = authService.login(requestDto);
        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Faz o registro do usuário ao sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Registro feito com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
            ),
            @ApiResponse(responseCode = "409", description = "Conflito já existe este usuário cadastrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class))
            )
        }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequestDto requestDto
            ){
        AuthResponse authResponse = authService.register(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

}
