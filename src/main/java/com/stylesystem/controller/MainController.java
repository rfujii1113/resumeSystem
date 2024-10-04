package com.stylesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("/login")
    public String loginUser() {
        return "login"; 
    }

    @GetMapping("/userMain")
    public String userMain() {
        return "userMain"; 
    }

    @GetMapping("/adminMain")
    public String adminMain() {
        return "adminMain"; 
    }
}
