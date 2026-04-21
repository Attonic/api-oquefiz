package io.github.oquefiz.controller;

import io.github.oquefiz.dto.Response.UserResponseDto;
import io.github.oquefiz.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Usuários")
public class UserController {

    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar todos os usuário")
    public ResponseEntity<Page<UserResponseDto>> findAll(
            Pageable pageable){
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Busca por usuário por Id")
    public ResponseEntity<UserResponseDto> findById(
            @PathVariable UUID id
            ){
        return ResponseEntity.ok(userService.findById(id));
    }


}
