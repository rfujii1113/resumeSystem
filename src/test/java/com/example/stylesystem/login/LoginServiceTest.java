package com.example.stylesystem.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.stylesystem.model.Employee;
import com.stylesystem.repository.EmployeeRepository;
import com.stylesystem.service.LoginService;

public class LoginServiceTest {

    private EmployeeRepository employeeRepository;
    private LoginService loginService;
    private BCryptPasswordEncoder passwordEncoder; 

    @BeforeEach
    public void setUp() {
        employeeRepository = mock(EmployeeRepository.class);  // Repository Mock
        passwordEncoder = new BCryptPasswordEncoder();  // BCryptPasswordEncoder

        loginService = new LoginService(employeeRepository);
    }

    @Test
    public void testAuthenticate_Success() {
    	// password encrypting test
        String rawPassword = "raw_password";
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // mock Employee data
        Employee employee = new Employee();
        employee.setEmpId(1);
        employee.setPassword(hashedPassword); 

        // search Employee by empNumber 
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Employee result = loginService.authenticate(1, rawPassword);
        
        assertEquals(1, result.getEmpId());
    }

    @Test
    public void testAuthenticate_Failure() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        Employee result = loginService.authenticate(1, "wrong_password");

        assertNull(result);
    }
}
