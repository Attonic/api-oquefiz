package io.github.oquefiz.service.impl;

import io.github.oquefiz.dto.Response.DailyResponse;
import io.github.oquefiz.model.Daily;
import io.github.oquefiz.model.Employee;
import io.github.oquefiz.repository.DailyRepository;
import io.github.oquefiz.repository.EmployeeRepository;
import org.checkerframework.checker.units.qual.UnknownUnits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DailyServiceImplTest {

    @Mock
    private DailyRepository dailyRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DailyServiceImpl dailyService;

    private Employee employee;
    private Daily daily;
    private UUID employeeId;
    private UUID dailyId;

    @BeforeEach
    void setup() {
        employeeId = UUID.randomUUID();
        dailyId = UUID.randomUUID();

        employee = Employee.builder()
                .employeeId(employeeId)
                .name("João Silva")
                .email("joao@empresa.com")
                .jobTitle("Dev Full Stack")
                .birthDate(LocalDate.of(2000, 9, 21))
                .build();

        daily = Daily.builder()
                .dailyId(dailyId)
                .whatIDid("Implementei endpoint de login")
                .difficulty("Configuração do JWT")
                .obstacle("Falta de documentação")
                .nextSteps("Escrever Testes")
                .dateRegister(LocalDate.now())
                .employee(employee)
                .build();
    }

    @Nested
    @DisplayName("findAll()")
    class FindAll {


        @Test
        @DisplayName("deve retornar página de dailies com sucesso")
        void deveRetornarPaginaDeDailies() {

            Pageable pageable = PageRequest.of(0, 10);
            Page<Daily> paginaMok = new PageImpl<>(List.of(daily));

            when(dailyRepository.findAll(any(Pageable.class))).thenReturn(paginaMok);


        }



    }

}
