package com.stylesystem.service;

import com.stylesystem.dto.UserAuthDto;
import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegistService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Users findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void registerNewUser(UserAuthDto userAuthDto) {
        // パスワードが一致しない場合は例外をスロー
        if (!userAuthDto.getPassword().equals(userAuthDto.getConfirmPassword())) {
            throw new IllegalArgumentException("パスワードが一致しません。");
        }

        // 既に存在するユーザーの場合は例外をスロー
        if (findByUserId(userAuthDto.getUserId()) != null) {
            throw new IllegalArgumentException("既に存在するユーザーです。別の社員番号を使用してください。");
        }

        // パスワードをハッシュ化して保存
        String encodedPassword = passwordEncoder.encode(userAuthDto.getPassword());
        userAuthDto.setPassword(encodedPassword);

        Users users = Users.builder()
                .userId(userAuthDto.getUserId())
                .password(userAuthDto.getPassword())
                .role(userAuthDto.getRole())
                .deleteFlag(false)
                .build();

        userRepository.save(users);
    }
}
