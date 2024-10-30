package com.stylesystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * アプリケーションのメインコントローラー。
 * ユーザーと管理者のメインページ、ログイン処理を管理します。
 */
@Controller
public class MainController {

    /**
     * ログインページを表示するメソッド。
     * ユーザーが認証済みの場合、ユーザーの権限に基づいてリダイレクト先を決定します。
     *
     * @param authentication 認証情報
     * @return 認証状態と権限に応じたリダイレクト先、またはログインページのビュー名
     */
    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // 管理者権限を持っている場合、管理者ページにリダイレクト
            if (authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/adminMain";
            }
            // ユーザーIDを取得し、ユーザーメインページにリダイレクト
            String userId = authentication.getName();
            return "redirect:/userMain?userId=" + userId;
        }
        // 認証されていない場合はログインページを表示
        return "login";
    }

    /**
     * 一般ユーザーのメインページを表示するメソッド。
     * 現在認証されているユーザーのIDをモデルに追加します。
     *
     * @param model ユーザーIDを格納するためのモデル
     * @return ユーザーメインページのビュー名
     */
    @GetMapping("/userMain")
    public String userMain(Model model) {
        // 現在のユーザーIDを取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        model.addAttribute("userId", userId);
        return "userMain";
    }

    /**
     * 管理者のメインページを表示するメソッド。
     *
     * @return 管理者メインページのビュー名
     */
    @GetMapping("/adminMain")
    public String adminMain() {
        return "adminMain";
    }
}
