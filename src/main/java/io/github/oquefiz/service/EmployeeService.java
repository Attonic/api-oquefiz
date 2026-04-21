package io.github.oquefiz.service;

import io.github.oquefiz.dto.Request.EmployeeRequest;
import io.github.oquefiz.dto.Response.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmployeeService {

    Page<EmployeeResponse> findAll(Pageable pageable);

    Page<EmployeeResponse> findByActiveTrue(Pageable pageable);

    EmployeeResponse findById(UUID uuid);

    EmployeeResponse findByUserId(UUID userId);

    EmployeeResponse create(EmployeeRequest employeeRequest);

    EmployeeResponse update(UUID employeeId, EmployeeRequest employeeRequest);

}
