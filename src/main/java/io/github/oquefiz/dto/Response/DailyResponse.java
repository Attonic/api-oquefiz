package io.github.oquefiz.dto.Response;

import io.github.oquefiz.model.Daily;
import io.github.oquefiz.model.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record DailyResponse(
        UUID dailyId,
        LocalDate dateRagister,
        String whatIDid,
        String difficulty,
        String obstacle,
        String nextSteps,
        LocalDateTime updatedAt,
        UUID employeeId,
        String employeeName

) {
    public static DailyResponse fromEntity(Daily daily){
        return new DailyResponse(
                daily.getDailyId(),
          daily.getDateRegister(),
          daily.getWhatIDid(),
          daily.getDifficulty(),
          daily.getObstacle(),
          daily.getNextSteps(),
          daily.getCreateAt(),
                daily.getEmployee().getEmployeeId(),
                daily.getEmployee().getName()
        );
    }
}
