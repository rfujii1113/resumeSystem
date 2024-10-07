package com.stylesystem.service;

import com.stylesystem.dto.UserAuthDto;
import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Users> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void registerNewUser(UserAuthDto userDto) {
        // パスワードが一致しない場合は例外をスロー
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new IllegalArgumentException("パスワードが一致しません。");
        }

        // 既に存在するユーザーの場合は例外をスロー
        if (findByUserId(userDto.getUserId()).isPresent()) {
            throw new IllegalArgumentException("既に存在するユーザーです。別の社員番号を使用してください。");
        }

        // パスワードをハッシュ化して保存
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        Users users = Users.builder()
            .userId(userDto.getUserId())
            .password(userDto.getPassword())
            .role(userDto.getRole())
            .deleteFlag(false)
            .build();

        userRepository.save(users);
    }
}
