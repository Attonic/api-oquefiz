package io.github.oquefiz.service.impl;

import io.github.oquefiz.dto.Request.EmployeeRequest;
import io.github.oquefiz.dto.Response.EmployeeResponse;
import io.github.oquefiz.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    private UUID employeeId;

    @BeforeEach
    void setUp() {
        EmployeeResponse colaborador = employeeService.create(new EmployeeRequest(
                "João Silva",
                "joao@empresa.com",
                "Dev Backend",
                LocalDate.of(1995, 5, 20),
                null
        ));
        employeeId = colaborador.employeeId();
    }

    private EmployeeRequest requestPadrao() {
        return new EmployeeRequest(
                 "João Silva",
                "joao@empresa.com",
                "Dev Backend",
                LocalDate.of(1995, 5, 20),
                null
        );
    }

    @Nested
    @DisplayName("create()")
    class Create {

        @Test
        @DisplayName("Deve criar colaborador e salvar no banco")
        void deveCriarColaboradorESalvarNoBanco() {
            EmployeeResponse response = employeeService.create(new EmployeeRequest(
                    "Maria Souza",
                    "maria@empresa.com",
                    "Dev Frontend",
                    LocalDate.of(1998, 3, 15),
                    null
            ));

            assertThat(response.employeeId()).isNotNull();
            assertThat(response.name()).isEqualTo("Maria Souza");
            assertThat(response.email()).isNull();
        }

    }

}
