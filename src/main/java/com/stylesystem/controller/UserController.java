package com.stylesystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // move login page
    @GetMapping("/login")
    public String showLoginPage() {
        return " resources/templates/login.html";  
    }

    // do login
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, 
                            @RequestParam String password, 
                            Model model) {
        UserDTO userDTO = userService.authenticateUser(email, password);
        
        if (userDTO != null) {
            // success login
            model.addAttribute("user", userDTO);
            return "employeeMain";  // Thymeleaf 템플릿 경로, resources/templates/employeeMain.html
        } else {
            // fail login
            model.addAttribute("loginError", "Invalid email or password");
            return "login";
        }
    }
}

