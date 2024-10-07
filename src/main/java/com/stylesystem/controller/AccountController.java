package com.stylesystem.controller;

import com.stylesystem.dto.UserAuthDto;
import com.stylesystem.service.UserRegistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserRegistService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userAuthDto", new UserAuthDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserAuthDto userAuthDto, Model model) {
        try {
            userService.registerNewUser(userAuthDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }
}
