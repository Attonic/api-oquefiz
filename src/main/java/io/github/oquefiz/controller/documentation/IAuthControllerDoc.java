package io.github.oquefiz.controller.documentation;

import io.github.oquefiz.dto.Request.LoginRequestDto;
import io.github.oquefiz.dto.Request.RegisterRequestDto;
import io.github.oquefiz.dto.Response.AuthResponse;
import io.github.oquefiz.exception.ConflictException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "Contém todas as operações responsáveis pelo login e registro de usuários.")
public interface IAuthControllerDoc {

    @Operation(summary = "Realiza login do usuário.", description = "Faz a autenticação com token JWT junto as credenciais do Usuário.",
        responses = {
                @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso.",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
                @ApiResponse(
                        responseCode = "401",
                        description = "Credenciais inválidas (E-mail ou senha incorretos)",
                        content = @Content()),
                @ApiResponse(
                responseCode = "400",
                description = "Dados da requisição inválidos (ex: e-mail em branco)",
                content = @Content
                )
        }
    )
    ResponseEntity<AuthResponse> login(LoginRequestDto requestDto);

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
    public ResponseEntity<AuthResponse> register(RegisterRequestDto requestDto);
}
