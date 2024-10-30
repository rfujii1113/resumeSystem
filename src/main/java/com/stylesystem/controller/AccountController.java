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

/**
 * ユーザーアカウントの登録、管理、メール送信機能を提供するコントローラークラス。
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserDeleteService userDeleteService;
    private final UserRegistService userService;
    private final UserRepository userRepository;

    /**
     * JavaMailSenderインターフェースを使用してメール送信を行うためのオブジェクト。
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 送信元のメールアドレスをプロパティファイルから取得。
     */
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * ユーザー登録フォームの表示を行うメソッド。
     * 
     * @param model モデルにUserAuthDtoオブジェクトを追加
     * @return ユーザー登録ページのビュー名
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userAuthDto", new UserAuthDto());
        return "register";
    }

    /**
     * 新規ユーザーの登録を行うメソッド。
     * 入力されたメールアドレスに初期パスワードを含むメールを送信し、ユーザー情報を保存する。
     * 
     * @param userAuthDto ユーザーの認証情報を含むDTO
     * @param model エラーメッセージを含むモデル
     * @return 登録が成功した場合はログインページへのリダイレクト、失敗した場合は再度登録ページを表示
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserAuthDto userAuthDto, Model model) {

        // メールメッセージの作成と送信
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userAuthDto.getEmail()); // 宛先メールアドレスをviewから取得
        message.setSubject("経歴書システムへようこそ"); // メールの件名
        message.setText("以下のページからログイン\n" +
                        "http://localhost:8080/login" +
                        "\n\n初期パスワード:" + userAuthDto.getPassword() +
                        "\n\n※このメールには返信を行わないでください※"); // メールの本文
        message.setFrom(fromEmail); // 送信元のメールアドレス
        mailSender.send(message); // メール送信

        // PWハッシュ化とユーザーの保存
        try {
            userService.registerNewUser(userAuthDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }

    /**
     * アカウント管理ページの表示を行うメソッド。
     * 削除フラグでソートされた全てのユーザーを取得してモデルに追加。
     * 
     * @param model モデルにユーザーリストを追加
     * @return アカウント管理ページのビュー名
     */
    @GetMapping("/management")
    public String accountManagement(Model model) {
        model.addAttribute("users", userRepository.findAllByOrderByDeleteFlag());
        return "accountManagement";
    }

    /**
     * 指定されたユーザーIDのアカウントを無効化するメソッド。
     * 
     * @param userId 無効化するユーザーのID
     * @return アカウント管理ページへのリダイレクト
     */
    @PostMapping("/deactivate")
    public String deactivateUser(@RequestParam("userId") String userId) {
        userDeleteService.deactivateUser(userId);
        return "redirect:/account/management";
    }

    /**
     * 指定されたユーザーIDのアカウントを有効化するメソッド。
     * 
     * @param userId 有効化するユーザーのID
     * @return アカウント管理ページへのリダイレクト
     */
    @PostMapping("/activate")
    public String activateUser(@RequestParam("userId") String userId) {
        userDeleteService.activateUser(userId);
        return "redirect:/account/management";
    }

}
