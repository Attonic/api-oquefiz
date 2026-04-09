package io.github.oquefiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(

        @NotBlank(message = "Nome de usuário é obrigatório.")
        @Size(min = 4, max = 80, message = "Nome deve ter entre 4 a 80 caracteres.")
        String userName,

        @NotBlank(message = "Senha é obrigatória.")
        @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
        String password,

        @NotBlank(message = "A Role é obrigatória")
        String role

) {



}
