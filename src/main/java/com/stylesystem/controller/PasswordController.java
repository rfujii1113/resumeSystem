package com.stylesystem.controller;

import com.stylesystem.dto.ResetPasswordDto;
import com.stylesystem.service.ResetPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordController {

    private final ResetPasswordService resetPasswordService;

    @GetMapping("/reset")
    public String showResetPasswordForm(Model model) {
        model.addAttribute("resetPasswordDto", new ResetPasswordDto());
        return "passwordReset";
    }

    @PostMapping("/reset")
    public String resetPassword(@ModelAttribute ResetPasswordDto resetPasswordDto, Model model) {
        // compare the new password and confirm password
        if (!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmPassword())) {
            model.addAttribute("errorMessage", "新しいパスワードが一致しません。");
            return "passwordReset";
        }

        try {
            // reset the password
            resetPasswordService.resetPassword(resetPasswordDto.getUserId(), resetPasswordDto.getNewPassword());
        } catch (IllegalArgumentException e) {
            // if the user does not exist
            model.addAttribute("errorMessage", e.getMessage());
            return "passwordReset";
        }

        return "redirect:/login";
    }
}
