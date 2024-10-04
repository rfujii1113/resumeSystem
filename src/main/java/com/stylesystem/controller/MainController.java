package com.stylesystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/adminMain";
            }
            return "redirect:/userMain";
        }
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
