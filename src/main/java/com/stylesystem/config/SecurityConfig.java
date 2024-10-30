package com.stylesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.stylesystem.service.LoginSuccessHandler;

/**
 * Spring Securityの設定クラス。
 * このクラスは、認証と認可のルール、ログインやログアウトの設定、
 * パスワードエンコーダーの設定を行います。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * ログイン成功時の動作を定義するハンドラー。
     */
    private final LoginSuccessHandler successHandler;

    /**
     * 指定されたログイン成功ハンドラーを使用して、SecurityConfigインスタンスを構築します。
     *
     * @param successHandler ログイン成功時に実行する処理を定義するハンドラー
     */
    public SecurityConfig(LoginSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    /**
     * アプリケーションのHTTPセキュリティ設定を構成します。
     * 認証と認可のルール、ログイン・ログアウトページ、CSRF無効化などを設定します。
     *
     * @param http 設定対象のHttpSecurityオブジェクト
     * @return 設定されたSecurityFilterChain
     * @throws Exception セキュリティ設定の構成に問題がある場合
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/account/register", "/css/**", "/js/**", "/static/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        return http.build();
    }

    /**
     * BCryptを使用してパスワードをハッシュ化するためのパスワードエンコーダーを設定します。
     *
     * @return パスワードエンコーダー
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
