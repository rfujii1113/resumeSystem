package com.stylesystem.controller;

import com.stylesystem.dto.UserDto;
import com.stylesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login"; // 로그인 페이지 뷰 이름
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register"; // 회원가입 페이지 뷰 이름
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.saveUser(userDto);
        return "redirect:/login";
    }
}
