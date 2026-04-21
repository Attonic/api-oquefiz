package io.github.oquefiz.dto.Response;

import io.github.oquefiz.model.Employee;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeResponse(
        UUID employeeId,
        UUID userId,
        String name,
        String email,
        String jobTitle,
        LocalDate birthDate
) {
    public static EmployeeResponse fromEntity(Employee employee){
        return new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getUser().getUserId(),
                employee.getName(),
                employee.getEmail(),
                employee.getJobTitle(),
                employee.getBirthDate()
        );
    }

}
