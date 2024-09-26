package com.stylesystem.service;

import com.stylesystem.model.Employee;
import com.stylesystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public LoginService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // 사용자 인증
    public Employee authenticate(String empName, int permission) {
        Optional<Employee> employeeOpt = employeeRepository.findByEmpNameAndPermission(empName, permission);
        return employeeOpt.orElse(null);
    }
}
