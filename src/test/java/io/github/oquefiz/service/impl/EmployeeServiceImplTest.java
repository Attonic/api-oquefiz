package io.github.oquefiz.service.impl;

import io.github.oquefiz.dto.Request.EmployeeRequest;
import io.github.oquefiz.dto.Response.EmployeeResponse;
import io.github.oquefiz.exception.ConflictException;
import io.github.oquefiz.exception.NotFoundException;
import io.github.oquefiz.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    private UUID employeeId;
    @Autowired
    private ResourceLoader resourceLoader;

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

        @Test
        @DisplayName("deve lançar ConflictException quando email já existir")
        void deveLancarConflictQuandoEmailJaExistir() {
            assertThatThrownBy(() -> employeeService.create(requestPadrao()))
                    .isInstanceOf(ConflictException.class);
        }

        @Test
        @DisplayName("deve lançar NotFoundException quando userId não existir no banco")
        void deveLancarNotFoundQuandoUserIdNaoExistir() {
            assertThatThrownBy(() ->
                    employeeService.create(new EmployeeRequest(
                            "Teste",
                            "teste@empresa.com",
                            "Dev",
                            LocalDate.now(),
                            UUID.randomUUID()
                    ))
            ).isInstanceOf(NotFoundException.class);
        }

    }

    @Nested
    @DisplayName("findById()")
    class FindById {

        @Test
        @DisplayName("Deve retornar colaborador quando id existir")
        void deveRetornarColaboradorQuandoIdExisir() {
            EmployeeResponse response = employeeService.findById(employeeId);

            assertThat(response.name()).isEqualTo("João Silva");
            assertThat(response.jobTitle()).isEqualTo("Dev Backend");
        }

        @Test
        @DisplayName("deve lançar NotFoundExeception quando o id não existir")
        void develancarNotFoudQuandoIdNaoExistir() {
            assertThatThrownBy(() -> employeeService.findById(UUID.randomUUID()))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("update()")
    class Update {

        @Test
        @DisplayName("Deve atualizar colaborador e persistir as mudanças")
        void deveAtualizarColaboradorEPersistirMudancas() {
            EmployeeResponse atualizado = employeeService.update(
                    employeeId,
                    new EmployeeRequest(
                            "João Silva Atualizado",
                            "joao@empresa.com",
                            "Tech Lead",
                            LocalDate.of(1995, 5, 20),
                            null
                    )
            );
            assertThat(atualizado.name()).isEqualTo("João Silva Atualizado");
            assertThat(atualizado.jobTitle()).isEqualTo("Tech Lead");
        }

        @Test
        @DisplayName("Deve lançar ConflictException ao trocar para email já usado por outro colaborador")
        void deveLancarConflictAoTrocarParaEmailJaExistente() {
            employeeService.create(new EmployeeRequest(
                    "Maria Souza",
                    "maria@empresa.com",
                    "QA",
                    LocalDate.now(),
                    null
            ));

            assertThatThrownBy(() ->
                    employeeService.update(employeeId, new EmployeeRequest(
                            "João Silva",
                            "maria@empresa.com",
                            "Dev Backend",
                            LocalDate.of(1995, 5, 20),
                            null
                    ))
            ).isInstanceOf(ConflictException.class);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException ao atualizar colaborador inexistente")
        void deveLancarNotFoundAoAtualizarInexistente() {
            assertThatThrownBy(() ->
                    employeeService.update(UUID.randomUUID(), requestPadrao())
            ).isInstanceOf(NotFoundException.class);
        }
    }

}
