package io.github.oquefiz.dto.Response;

public record AuthResponse(
        String token,
        String refreshToken,
        String type,
        UserResponseDto user
) {

    public AuthResponse(String token, String refreshToken, UserResponseDto userResponseDto){
        this(token, refreshToken, "Bearer", userResponseDto);
    }

}
