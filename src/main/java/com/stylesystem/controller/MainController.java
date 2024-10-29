package com.stylesystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
             // Get the current user's ID
             String userId = authentication.getName();
             return "redirect:/userMain?userId=" + userId;
        }
        return "login";
    }

    @GetMapping("/userMain")
    public String userMain(Model model) {
        // Get the current user's ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        model.addAttribute("userId", userId);
        return "userMain";
    }

    @GetMapping("/adminMain")
    public String adminMain() {
        return "adminMain";
    }

}
