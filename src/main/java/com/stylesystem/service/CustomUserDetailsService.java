package com.stylesystem.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stylesystem.model.Employee;
import com.stylesystem.repository.EmployeeRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username을 empId로 간주하고 파싱
        int empId;
        try {
            empId = Integer.parseInt(username);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid employee ID format");
        }

        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with ID: " + empId));

        // 권한 설정
        String role = (employee.getPermission() == 1) ? "ADMIN" : "EMPLOYEE";

        return User.builder()
                .username(String.valueOf(employee.getEmpId()))
                .password(employee.getPassword())
                .roles(role)
                .build();
    }
}
