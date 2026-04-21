package io.github.oquefiz.controller;

import io.github.oquefiz.dto.Request.EmployeeRequest;
import io.github.oquefiz.dto.Response.EmployeeResponse;
import io.github.oquefiz.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Colaboradores")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/ativos")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista Todos os Colaboradores ativos")
    public ResponseEntity<Page<EmployeeResponse>> findAllAtivo(
            Pageable pageable
    ){
        return ResponseEntity.ok(employeeService.findByActiveTrue(pageable));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista todos os colaboradores")
    public ResponseEntity<Page<EmployeeResponse>> findAll(
            Pageable pageable
    ){
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Busca colaborador por Id")
    public ResponseEntity<EmployeeResponse> findById(
            @PathVariable UUID id 
            ){
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastra novo colaborador.")
    public ResponseEntity<EmployeeResponse> create(
            @RequestBody @Valid EmployeeRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza um colaborador")
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeRequest request
    ){
        return ResponseEntity.ok(employeeService.update(id, request));
    }


}
