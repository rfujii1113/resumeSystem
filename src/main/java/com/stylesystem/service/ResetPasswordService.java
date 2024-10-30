package com.stylesystem.service;

import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * ユーザーのパスワードリセットを行うサービスクラス。
 * ユーザーが存在するかと新しいパスワードが現在のパスワードと異なるかを確認し、パスワードを更新します。
 */
@Service
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * ResetPasswordServiceのコンストラクタ。
     *
     * @param userRepository ユーザー情報を管理するリポジトリ
     * @param passwordEncoder パスワードのエンコードを行うエンコーダ
     */
    public ResetPasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 指定されたユーザーIDのパスワードを新しいパスワードでリセットします。
     *
     * @param userId パスワードをリセットするユーザーID
     * @param newPassword 新しいパスワード
     * @throws IllegalArgumentException ユーザーが存在しない場合、または新しいパスワードが現在のパスワードと同じ場合にスローされます
     */
    public void resetPassword(String userId, String newPassword) {
        Users user = userRepository.findByUserId(userId);
        
        // ユーザーが存在するかを確認
        if (user == null) {
            throw new IllegalArgumentException("該当するユーザーが見つかりません。");
        }

        // 新しいパスワードが現在のパスワードと同じでないことを確認
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("新しいパスワードは現在のパスワードと同じです。");
        }

        // 新しいパスワードを保存
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
