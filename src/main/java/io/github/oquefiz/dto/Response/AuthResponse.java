package io.github.oquefiz.dto.Response;

public record AuthResponse(
        String token,
        String type,
        UserResponseDto user
) {

    public AuthResponse(String token,  UserResponseDto userResponseDto){
        this(token,  "Bearer", userResponseDto);
    }

}
