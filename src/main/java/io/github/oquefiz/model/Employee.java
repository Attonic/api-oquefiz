package io.github.oquefiz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {

    @Column(name = "employee_id", unique = true)
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID employeeId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "job_title", nullable = false, unique = true, length = 100)
    private String jobTile;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;



}
