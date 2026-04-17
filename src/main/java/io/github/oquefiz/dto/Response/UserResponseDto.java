package io.github.oquefiz.dto.Response;

import io.github.oquefiz.model.User;
import io.github.oquefiz.model.enums.UserRole;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


public record UserResponseDto(
        UUID userId,
        String userName,
        String email,
        UserRole role
) {
    public static UserResponseDto fromEntity(User user){
        return new UserResponseDto(
          user.getUserId(),
          user.getUserName(),
          user.getEmail(),
          user.getRole()
        );
    }
}
