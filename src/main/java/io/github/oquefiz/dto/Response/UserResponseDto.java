package io.github.oquefiz.dto.Response;

import io.github.oquefiz.model.User;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


public record UserResponseDto(
        UUID userId,
        String userName,
        String email,
        LocalDateTime createdAt
) {
    public static UserResponseDto fromEntity(User user){
        return new UserResponseDto(
          user.getUserId(),
          user.getUsername(),
          user.getEmail(),
          user.getCreatedAt()
        );
    }
}
