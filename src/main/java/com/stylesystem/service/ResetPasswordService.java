package com.stylesystem.service;

import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResetPasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void resetPassword(String userId, String newPassword) {
        // find user by userId
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("該当するユーザーが見つかりません。"));

        // check if new password is the same as the current password
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("新しいパスワードは現在のパスワードと同じです。");
        }

        // save new password
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
