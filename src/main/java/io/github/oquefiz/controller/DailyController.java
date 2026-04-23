package io.github.oquefiz.controller;

import io.github.oquefiz.dto.Request.DailyRequestDto;
import io.github.oquefiz.dto.Response.DailyResponse;
import io.github.oquefiz.service.DailyService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dailies")
public class DailyController {

    private final DailyService dailyService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Busca todas as dailies")
    public ResponseEntity<Page<DailyResponse>> findAll(
            Pageable pageable
    ){
        return ResponseEntity.ok(dailyService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Busca daily por id")
    public ResponseEntity<DailyResponse> findById(
            @PathVariable UUID id
            ){
        return ResponseEntity.ok(dailyService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cria uma nova Daily")
    public ResponseEntity<DailyResponse> create(
            @Valid @RequestBody DailyRequestDto requestDto
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(dailyService.createDaily(requestDto));
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    @Operation(summary = "Atualizar uma Daily")
//    public ResponseEntity<DailyResponse> update(
//        @PathVariable UUID id,
//        @Valid @RequestBody DailyRequestDto dailyRequestDto
//    ){
//        return ResponseEntity.ok(dailyService.(id, dailyRequestDto));
//    }

}
