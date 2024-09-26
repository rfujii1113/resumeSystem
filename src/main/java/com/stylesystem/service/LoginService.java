package com.stylesystem.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stylesystem.model.Employee;
import com.stylesystem.repository.EmployeeRepository;
import com.stylesystem.util.PasswordHashGenerator;

@Service
public class LoginService {

    private final EmployeeRepository employeeRepository;

    // DI EmployeeRepository in constructor 
    public LoginService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // login method
    public Employee authenticate(int empId, String password) {
        Optional<Employee> employeeOpt = employeeRepository.findById(empId);
        
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            if (PasswordHashGenerator.matchPassword(password, employee.getPassword())) {
                return employee;  // login success 
            }
        }
        return null;  // login fail
    }
}
