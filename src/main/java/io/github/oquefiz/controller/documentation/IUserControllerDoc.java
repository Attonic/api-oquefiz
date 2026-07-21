package io.github.oquefiz.controller.documentation;

import io.github.oquefiz.dto.Response.UserResponseDto;
import io.github.oquefiz.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Usuário")
public interface IUserControllerDoc {

    @Operation(summary = "Buscar todos os usuário", description = "Traz todos os usuários cadastrados",
            responses = {
                @ApiResponse(responseCode = "200", description = "Usuários encontrado com sucesso.",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                )
            }
    )
    ResponseEntity<Page<UserResponseDto>> findAll(Pageable pageable);

    @Operation(summary = "Busca por usuário por Id", description = "Traz um usuário pelo seu Id.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado como autorizado.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado para o Id.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))
            )
        }
    )
    ResponseEntity<UserResponseDto> findById(UUID id);

}
