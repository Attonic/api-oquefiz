package io.github.oquefiz.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequestDto(
        @NotBlank( message = "Email deve ser informado.")
        @Email(message = "Email deve ser válido.")
        String Email,
        @NotBlank(message = "Senha deve ser informada.")
        String password
) {

}
