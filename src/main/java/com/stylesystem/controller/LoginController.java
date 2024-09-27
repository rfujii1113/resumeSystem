package com.stylesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.stylesystem.dto.LoginRequestDTO;
import com.stylesystem.model.Employee;
import com.stylesystem.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDTO loginRequest, Model model) {
        Employee employee = loginService.authenticate(loginRequest.getEmpId(), loginRequest.getPassword());
        if (employee != null) {
            model.addAttribute("employee", employee);
            if (employee.getPermission() == 1) {
                return "adminMain";
            } else {
                return "employeeMain";
            }
        } else {
            model.addAttribute("loginError", "Invalid employee number or password");
            return "login"; 
        }
    }
}
