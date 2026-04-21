package io.github.oquefiz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "dailies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Daily {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID dailyId;

    @Column(name = "date_register")
    private LocalDate dateRegister = LocalDate.now();

    @Column(name = "what_i_did", length = 1000, nullable = false)
    private String whatIDid;

    @Column(name = "dificulty", length = 1000)
    private String difficulty;

    @Column(name = "obstacle", length = 1000)
    private String obstacle;

    @Column(name = "next_steps", length = 1000)
    private String nextSteps;

    @CreationTimestamp
    @Column(name = "creat_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @CreationTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;



}
