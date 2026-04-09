package io.github.oquefiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DailyDto(
        UUID dailyId,
        LocalDate dateRegister,

        @NotBlank(message = "O Campo 'O que fiz' é obrigatório")
        @Size(max = 1000)
        String whatIDid,

        @Size(max = 1000)
        String difficult,

        @Size(max = 1000)
        String obstacle,

        @Size(max = 1000)
        String nextSteps,

        @Size(max = 1000)
        UUID employeeId
) {
}
