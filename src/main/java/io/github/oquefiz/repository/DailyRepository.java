package io.github.oquefiz.repository;

import io.github.oquefiz.model.Daily;
import io.github.oquefiz.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailyRepository extends JpaRepository<Daily, UUID> {

    Optional<Daily> findByEmployee(Employee employee);

}
