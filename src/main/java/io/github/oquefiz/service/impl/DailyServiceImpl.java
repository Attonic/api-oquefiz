package io.github.oquefiz.service.impl;

import io.github.exception.NotFoundException;
import io.github.oquefiz.dto.Request.DailyRequestDto;
import io.github.oquefiz.dto.Response.DailyResponse;
import io.github.oquefiz.model.Daily;
import io.github.oquefiz.model.Employee;
import io.github.oquefiz.repository.DailyRepository;
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

    @Override
    @Transactional(readOnly = true)
    public Page<DailyResponse> findAll(Pageable pageable) {
        return dailyRepository.findAll(pageable)
                .map(DailyResponse::fronEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyResponse findById(UUID uuid) {
        Daily daily = dailyRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Daily não encontrada."));
        return DailyResponse.fronEntity(daily);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyResponse findByEmployee(Employee employee) {
        Daily daily = dailyRepository.findByEmployee(employee)
                .orElseThrow(() -> new NotFoundException("Daily não encontrada."));
        return DailyResponse.fronEntity(daily);
    }

    @Override
    @Transactional
    public DailyResponse createDaily(DailyRequestDto dailyRequestDto) {
        return null;
    }
}
