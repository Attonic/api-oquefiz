package io.github.oquefiz.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequestDto(

        @NotBlank(message = "A senha deve ser informada.")
        String currentPassword,

        @NotBlank(message = "Nova Senha deve ser informada.")
        @Size(min = 6, max = 100)
        String newPassword
) {
}
