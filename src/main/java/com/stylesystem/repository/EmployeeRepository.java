package com.stylesystem.repository;

import com.stylesystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmpNameAndPermission(String empName, int permission);
}
