package io.github.oquefiz.controller.documentation;

import io.github.oquefiz.dto.Request.EmployeeRequest;
import io.github.oquefiz.dto.Response.EmployeeResponse;
import io.github.oquefiz.exception.ConflictException;
import io.github.oquefiz.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Employee (Colaboradores)",
        description = "Contém todas as operações responsáveis por Colaboradores")
public interface IEmployeeControllerDoc {

    @Operation(summary = "Lista Todos os Colaboradores ativos",
            description = "Traz todos os Colaboradores cadastrados de forma paginada.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Colaboradores ativos carregados com sucesso.",
                content = @Content(mediaType = "application/json", array = @ArraySchema( schema = @Schema(implementation = EmployeeResponse.class)))
            ),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido."),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui a role ADMIN.")
        }
    )
    ResponseEntity<Page<EmployeeResponse>> findAllAtivo(Pageable pageable);

    @Operation(summary = "Busca todas os colaboradores.", description = "Carrega todas os colaboradores cadastradas de forma paginada.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Colaboradores carregadas com sucesso.",
                       content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EmployeeResponse.class)))),
                @ApiResponse(responseCode = "401", description = "Token ausente ou inválido."),
                @ApiResponse(responseCode = "403", description = "Usuário autenticado não possui a role ADMIN.")
            }
    )
    ResponseEntity<Page<EmployeeResponse>> findAll(Pageable pageable);


    @Operation(summary = "Busca colaborador por id", description = "Faz a busca de um colaborador pelo seu ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Colaborador encontrada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Colaborador não encontrada para o Id.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))
            )
        }
    )
    ResponseEntity<EmployeeResponse> findById( UUID id);

    @Operation(summary = "Cadastra um colaborador.", description = "Faz o cadastro de um novo colaborador.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Colaborador criado com sucesso.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email usado já existe para um Colaborador.",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class))
            )
        }
    )
    ResponseEntity<EmployeeResponse> create(@Valid EmployeeRequest request);

    @Operation(summary = "Atualiza um colaborador.", description = "Faz o cadastro de um novo colaborador.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Colaborador atualizado com sucesso.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email usado já existe para um Colaborador.",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class))
            )
        }
    )
    ResponseEntity<EmployeeResponse> update(UUID id, EmployeeRequest request);

}
