package com.stylesystem.controller;

import com.stylesystem.dto.UserAuthDto;
import com.stylesystem.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final RegisterService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginUser() {
        return "login"; 
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new UserAuthDto());
        return "register"; 
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserAuthDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userService.saveUser(userDto);  
        return "redirect:/login";
    }

    @GetMapping("/userMain")
    public String userMain() {
        return "userMain"; 
    }

    @GetMapping("/adminMain")
    public String adminMain() {
        return "adminMain";
    }

    @GetMapping("/passwordReset")
    public String passwordReset() {
        return "passwordReset";
    }

}
