package io.github.oquefiz.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthRequestDto(
        @NotBlank
        String Email,
        @NotBlank
        String password
) {

}
