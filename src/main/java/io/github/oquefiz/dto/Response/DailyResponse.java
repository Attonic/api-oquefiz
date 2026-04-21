package io.github.oquefiz.dto.Response;

import io.github.oquefiz.model.Daily;
import io.github.oquefiz.model.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DailyResponse(

        LocalDate dateRagister,
        String whatIDid,
        String difficulty,
        String obstacle,
        String nextSteps,
        LocalDateTime updatedAt,
        Employee employee

) {
    public static DailyResponse fromEntity(Daily daily){
        return new DailyResponse(
          daily.getDateRegister(),
          daily.getWhatIDid(),
          daily.getDifficulty(),
          daily.getObstacle(),
          daily.getNextSteps(),
          daily.getCreateAt(),
          daily.getEmployee()
        );
    }
}
