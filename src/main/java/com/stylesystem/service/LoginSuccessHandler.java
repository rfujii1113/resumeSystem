package com.stylesystem.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ログイン成功時にユーザーの役割に基づいてリダイレクト先を指定するためのハンドラークラス。
 * 管理者は管理者用のページへ、それ以外のユーザーは通常のユーザーページにリダイレクトされます。
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 認証成功時に呼び出され、ユーザーの役割に応じてリダイレクトを行います。
     *
     * @param request      HTTPリクエスト
     * @param response     HTTPレスポンス
     * @param authentication 認証情報を含むオブジェクト
     * @throws IOException 入出力例外が発生した場合
     * @throws ServletException サーブレット例外が発生した場合
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
                                       
        String role = authentication.getAuthorities().toString();

        if (role.contains("ROLE_ADMIN")) {
            response.sendRedirect("/adminMain");
        } else {
            response.sendRedirect("/userMain");
        }
    }
}
