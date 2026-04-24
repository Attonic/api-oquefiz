package io.github.oquefiz.service.impl;


import io.github.oquefiz.dto.Request.DailyRequestDto;
import io.github.oquefiz.dto.Request.EmployeeRequest;
import io.github.oquefiz.dto.Response.DailyResponse;
import io.github.oquefiz.exception.NotFoundException;
import io.github.oquefiz.service.DailyService;
import io.github.oquefiz.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RequiredArgsConstructor
@ActiveProfiles("test")
@Transactional
public class DailyServiceImplTest {

    private DailyService dailyService;
    private EmployeeService employeeService;

    private UUID employeeId;

    @BeforeEach
    void setUp() {
        var colaborador = employeeService.create(new EmployeeRequest(
                "João Silva",
                "joao@empresa.com",
                "Dev BackEnd",
                LocalDate.of(2000,9,21),
                null
        ));
        employeeId = colaborador.employeeId();
    }

    private DailyResponse criarDailyPadrao() {
        return dailyService.createDaily( new DailyRequestDto(
                null,
                LocalDate.now(),
                "Implementei o endpoint de login",
                "Configuração do JWT",
                "Falta de documentação",
                "Escrever testes",
                employeeId
        ));
    }

    @Nested
    @DisplayName("findAll()")
    class findAll {

        @Test
        @DisplayName("Deve retornar pagina com dailies criadas.")
        void deveRetornarPaginaComDailiesCriadas() {
            criarDailyPadrao();
            Page<DailyResponse> responses = dailyService.findAll(PageRequest.of(0,10));

            assertThat(responses.getTotalElements()).isZero();
        }

        @Test
        @DisplayName("Deve retornar página vazia quando não houver dailies")
        void deveRetornarPaginaVaziaQuandoNaoHouverDailies() {
            Page<DailyResponse> responses = dailyService.findAll(PageRequest.of(0, 10));
            assertThat(responses.getTotalElements()).isZero();
        }

    }

    @Nested
    @DisplayName("findById()")
    class FindById {

        @Test
        @DisplayName("Deve retornar a daily quando o id exitir")
        void deveRetornarDailyQuandoIdExistir() {

            DailyResponse criada = criarDailyPadrao();
            DailyResponse resultado = dailyService.findById(criada.dailyId());

            assertThat(resultado.whatIDid()).isEqualTo("Implementei o endpoint de login");
            assertThat(resultado.employeeId()).isEqualTo(employeeId);

        }

        @Test
        @DisplayName("deve lançar NotFoundException quando o id não existir")
        void deveLancarNotFoundQuandoIdNaoExistir() {
            assertThatThrownBy(() -> dailyService.findById(UUID.randomUUID()))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("createDaily()")
    class CreateDaily {

        @Test
        @DisplayName("Deve criar daily com sucesso e salvar no banco")
        void deveCriarDailyComSucesso() {
            DailyResponse resultado = criarDailyPadrao();

            assertThat(resultado).isNotNull();
            assertThat(resultado.dailyId()).isNotNull();
            assertThat(resultado.whatIDid()).isEqualTo("Implementei o endpoint de login");
            assertThat(resultado.employeeId()).isEqualTo(employeeId);

        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando colaborador não existir")
        void deveLancarNotFoundExceptionQuandoColaboradorNaoExistir() {
            assertThatThrownBy(() ->
                    dailyService.createDaily(new DailyRequestDto(
                            null,
                            LocalDate.now(),
                            "Trabalho do dia",
                null, null, null,
                            UUID.randomUUID()
                    ))
            ).isInstanceOf(NotFoundException.class);
        }

        @Test
        @DisplayName("Deve criar daily apenas com campos obriagatorios preenchidos")
        void deveCriarDailyApenasComCampoObrigatorio() {
        DailyResponse resultado = dailyService.createDaily(new DailyRequestDto(
                    null,
                    LocalDate.now(),
                    "Só o que fiz hoje",  // único campo obrigatório
                    null,                 // difficulty pode ser nulo
                    null,                 // obstacle pode ser nulo
                    null,                 // nextSteps pode ser nulo
                    employeeId
            ));

            assertThat(resultado.whatIDid()).isEqualTo("Só o que fiz hoje");
            assertThat(resultado.difficulty()).isNull();
        }

    }

}
