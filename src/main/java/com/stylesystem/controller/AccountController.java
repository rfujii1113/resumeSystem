package com.stylesystem.controller;

import com.stylesystem.repository.UserRepository;
import com.stylesystem.dto.UserAuthDto;
import com.stylesystem.service.UserDeleteService;
import com.stylesystem.service.UserRegistService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserDeleteService userDeleteService;
    private final UserRegistService userService;
    private final UserRepository userRepository;

    //JavaMail 用の拡張 MailSender インターフェース。
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}") // プロパティから送信元アドレスを取得
    private String fromEmail;


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userAuthDto", new UserAuthDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserAuthDto userAuthDto, Model model) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userAuthDto.getEmail()); // 宛先メールアドレスをviewから取得
        message.setSubject("経歴書システムへようこそ"); // メールの件名
        message.setText("以下のページからログイン\n"+"http://localhost:8080/login"+"\n\n初期パスワード:"+userAuthDto.getPassword()+"\n\n※このメールには返信を行わないでください※"); // メールの本文
        message.setFrom(fromEmail); // 送信元のメールアドレス
        mailSender.send(message); // メール送信

        //PWハッシュ化ロジック
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
        //usersテーブルのデータを全件取得delete_flagの順に並び替えmodelに格納
        model.addAttribute("users", userRepository.findAllByOrderByDeleteFlag());
        return "accountManagement";
    }

    @PostMapping("/deactivate")
    public String deactivateUser(@RequestParam("userId") String userId) {
        userDeleteService.deactivateUser(userId);
        return "redirect:/account/management";
    }

    @PostMapping("/activate")
    public String activateUser(@RequestParam("userId") String userId) {
        userDeleteService.activateUser(userId);
        return "redirect:/account/management";
    }

}