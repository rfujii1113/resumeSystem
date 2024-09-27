package com.stylesystem.service;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.stylesystem.model.Employee;
import com.stylesystem.repository.EmployeeRepository;

@Service
public class LoginService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    // DI EmployeeRepository and PasswordEncoder in constructor
    public LoginService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // login method
    public Employee authenticate(int empId, String password) {
        Optional<Employee> employeeOpt = employeeRepository.findById(empId);
        
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            // Compare raw password with encoded password using Spring Security's PasswordEncoder
            if (passwordEncoder.matches(password, employee.getPassword())) {
                return employee;  // login success
            }
        }
        return null;  // login fail
    }
}
