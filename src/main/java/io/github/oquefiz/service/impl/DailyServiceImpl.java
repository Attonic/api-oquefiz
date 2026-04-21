package io.github.oquefiz.service.impl;

import io.github.exception.NotFoundException;
import io.github.oquefiz.dto.Request.DailyRequestDto;
import io.github.oquefiz.dto.Response.DailyResponse;
import io.github.oquefiz.model.Daily;
import io.github.oquefiz.model.Employee;
import io.github.oquefiz.repository.DailyRepository;
import io.github.oquefiz.repository.EmployeeRepository;
import io.github.oquefiz.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DailyServiceImpl implements DailyService {

    private final DailyRepository dailyRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<DailyResponse> findAll(Pageable pageable) {
        return dailyRepository.findAll(pageable)
                .map(DailyResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyResponse findById(UUID uuid) {
        Daily daily = dailyRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Daily não encontrada."));
        return DailyResponse.fromEntity(daily);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyResponse findByEmployee(UUID employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Colaborador não encontrado."));

        Daily daily = dailyRepository.findByEmployee_EmployeeId(employeeId)
                .orElseThrow(() -> new NotFoundException("Daily não encontrada."));

        return DailyResponse.fromEntity(daily);
    }

    @Override
    @Transactional
    public DailyResponse createDaily(DailyRequestDto dailyRequestDto) {
        Employee employee = employeeRepository.findById(dailyRequestDto.employeeId())
                .orElseThrow(() -> new NotFoundException("Colaborador não encontrado."));

        Daily daily = Daily.builder()
                .dateRegister(dailyRequestDto.dateRegister())
                .whatIDid(dailyRequestDto.whatIDid())
                .difficulty(dailyRequestDto.difficult())
                .obstacle(dailyRequestDto.obstacle())
                .nextSteps(dailyRequestDto.nextSteps())
                .employee(employee)
                .build();

        dailyRepository.save(daily);
        return DailyResponse.fromEntity(daily);
    }
}
