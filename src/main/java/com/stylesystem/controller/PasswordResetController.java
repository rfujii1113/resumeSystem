package com.stylesystem.controller;

import com.stylesystem.dto.UserPasswordResetDto;
import com.stylesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordResetController {

    private final UserService userService;

    @GetMapping("/reset")
    public String showResetPasswordForm(Model model) {
        model.addAttribute("userPasswordResetDto", new UserPasswordResetDto());
        return "passwordReset";
    }

    @PostMapping("/reset")
    public String resetPassword(@ModelAttribute UserPasswordResetDto userPasswordResetDto) {
        userService.resetPassword(userPasswordResetDto);
        return "redirect:/login"; 
    }
}
