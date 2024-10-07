package com.stylesystem.controller;

import com.stylesystem.dto.UserInfoDto;
import com.stylesystem.dto.UserAuthDto;
import com.stylesystem.service.UserDeleteService;
import com.stylesystem.service.UserRegistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserDeleteService userDeleteService;
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

    @GetMapping("/management")
    public String accountManagement(Model model) {
        List<UserInfoDto> userInfoList = userDeleteService.getAllActiveUsers().stream()
                .map(UserInfoDto::fromEntity)
                .collect(Collectors.toList());
        model.addAttribute("users", userInfoList);
        return "accountManagement";
    }

    @PostMapping("/deactivate")
    public String deactivateUser(@RequestParam("userId") String userId) {
        System.out.println("Deactivating user with ID: " + userId); // for debugging
        userDeleteService.deactivateUser(userId);
        return "redirect:/account/management";
    }

    @PostMapping("/activate")
    public String activateUser(@RequestParam("userId") String userId) {
        userDeleteService.activateUser(userId);
        return "redirect:/account/management";
    }

}