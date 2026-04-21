package io.github.oquefiz.repository;

import io.github.oquefiz.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Page<Employee> findAllByUser_ActiveTrue();

    boolean existsByEmail(String email);

    Optional<Employee> findByUser_UserId(UUID userUserId);



}
