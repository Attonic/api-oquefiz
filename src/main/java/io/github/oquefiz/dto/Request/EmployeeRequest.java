package io.github.oquefiz.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeRequest(

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 150)
    String name,

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email inválido.")
    @Size(max = 150)
    String email,

    @NotBlank(message = "Titulo ou Cargo é obrigatório.")
    @Size(max = 150)
    String jobTitle,

    @NotNull(message = "Data de Nascimento deve ser Obrigatório.")
    LocalDate birthDate,

    UUID userId

) {
}
