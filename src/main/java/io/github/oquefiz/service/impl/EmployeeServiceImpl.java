package io.github.oquefiz.service.impl;

import io.github.oquefiz.exception.ConflictException;
import io.github.oquefiz.exception.NotFoundException;
import io.github.oquefiz.dto.Request.EmployeeRequest;
import io.github.oquefiz.dto.Response.EmployeeResponse;
import io.github.oquefiz.model.Employee;
import io.github.oquefiz.model.User;
import io.github.oquefiz.repository.EmployeeRepository;
import io.github.oquefiz.repository.UserRepository;
import io.github.oquefiz.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(EmployeeResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> findByActiveTrue(Pageable pageable) {
        return employeeRepository.findAllByUser_ActiveTrue(pageable)
                .map(EmployeeResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse findById(UUID uuid) {
        return employeeRepository.findById(uuid)
                .map(EmployeeResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Colaborador não encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse findByUserId(UUID userId) {
        return employeeRepository.findByUser_UserId(userId)
                .map(EmployeeResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Colaborador não encontrado"));
    }

    @Override
    @Transactional
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        if (employeeRepository.existsByEmail(employeeRequest.email())){
            throw new ConflictException("Já existe colaborador para esse Email");
        }

        Employee employee = Employee.builder()
                .name(employeeRequest.name())
                .email(employeeRequest.email())
                .jobTitle(employeeRequest.jobTitle())
                .birthDate(employeeRequest.birthDate())
                .build();

        if (employeeRequest.userId() != null){
            User user = userRepository.findById(employeeRequest.userId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
            employee.setUser(user);
        }

        employeeRepository.save(employee);

        return EmployeeResponse.fromEntity(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse update(UUID employeeId, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Colaborador não encontrado."));

        if (!employeeRequest.email().equals(employee.getEmail()) && employeeRepository.existsByEmail(employeeRequest.email())){
            throw new ConflictException("Já existe colaborador com esse email.");
        }

        employee.setName(employeeRequest.name());
        employee.setEmail(employeeRequest.email());
        employee.setJobTitle(employeeRequest.jobTitle());
        employee.setBirthDate(employeeRequest.birthDate());

        if (employeeRequest.userId() != null) {
            User user = userRepository.findById(employeeRequest.userId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
            employee.setUser(user);
        }

         employeeRepository.save(employee);

        return EmployeeResponse.fromEntity(employee);
    }
}
