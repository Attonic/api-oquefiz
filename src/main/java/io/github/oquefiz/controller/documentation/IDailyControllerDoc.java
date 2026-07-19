package io.github.oquefiz.controller.documentation;

import io.github.oquefiz.dto.Request.DailyRequestDto;
import io.github.oquefiz.dto.Response.DailyResponse;
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

@Tag(name = "Daily", description = "Contém todas as operações responsáveis pelas Dailies.")
public interface IDailyControllerDoc {

    @Operation(summary = "Busca todas as dailies.", description = "Carrega todas as Dailies cadastradas de forma paginada.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Dailies carregadas com sucesso.", content = @Content()),
            }
    )
    ResponseEntity<Page<DailyResponse>> findAll(Pageable pageable);


    @Operation(summary = "Busca daily por id", description = "Faz a busca de uma daily pelo seu ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Daily encontrada com sucesso", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Daily não encontrada para o Id.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))
            )
        }
    )
    ResponseEntity<DailyResponse> findById( UUID id);

    @Operation(summary = "Cria uma nova Daily", description = "Cria uma daily e devolve um response da mesma.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Daily criada com sucesso.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DailyResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Empregado referente a daily não encontrado.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))
            )
        }
    )
    ResponseEntity<DailyResponse> create(DailyRequestDto requestDto);

}
