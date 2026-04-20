package io.github.oquefiz.service;

import io.github.oquefiz.dto.Request.DailyRequestDto;
import io.github.oquefiz.dto.Response.DailyResponse;
import io.github.oquefiz.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.UUID;

public interface DailyService {

    Page<DailyResponse> findAll(Pageable pageable);

    DailyResponse findById(UUID uuid);

    DailyResponse findByEmployee(Employee employee);

    DailyResponse createDaily(DailyRequestDto dailyRequestDto);


}
