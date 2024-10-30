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

/**
 * パスワードのリセット機能を提供するコントローラークラス。
 * ユーザーが新しいパスワードを設定できるフォームを表示し、リセット操作を実行します。
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordController {

    /**
     * パスワードリセットのサービスクラス。
     */
    private final ResetPasswordService resetPasswordService;

    /**
     * パスワードリセットフォームを表示するメソッド。
     *
     * @param model モデルにリセット用のDTOを追加
     * @return パスワードリセットページのビュー名
     */
    @GetMapping("/reset")
    public String showResetPasswordForm(Model model) {
        model.addAttribute("resetPasswordDto", new ResetPasswordDto());
        return "passwordReset";
    }

    /**
     * パスワードのリセットを実行するメソッド。
     * 新しいパスワードと確認パスワードを比較し、一致しない場合はエラーメッセージを表示します。
     * ユーザーが存在しない場合もエラーを返します。
     *
     * @param resetPasswordDto 新しいパスワード情報を含むDTO
     * @param model エラーメッセージを格納するためのモデル
     * @return 成功した場合はログインページへのリダイレクト、エラー時はリセットページのビュー名
     */
    @PostMapping("/reset")
    public String resetPassword(@ModelAttribute ResetPasswordDto resetPasswordDto, Model model) {
        // 新しいパスワードと確認パスワードを比較
        if (!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmPassword())) {
            model.addAttribute("errorMessage", "新しいパスワードが一致しません。");
            return "passwordReset";
        }

        try {
            // パスワードのリセットを実行
            resetPasswordService.resetPassword(resetPasswordDto.getUserId(), resetPasswordDto.getNewPassword());
        } catch (IllegalArgumentException e) {
            // ユーザーが存在しない場合のエラーメッセージ
            model.addAttribute("errorMessage", e.getMessage());
            return "passwordReset";
        }

        return "redirect:/login";
    }
}
