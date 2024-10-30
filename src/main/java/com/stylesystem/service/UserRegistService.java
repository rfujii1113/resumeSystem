package com.stylesystem.service;

import com.stylesystem.dto.UserAuthDto;
import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 新規ユーザー登録を管理するサービスクラス。
 * ユーザーの登録と、ユーザーIDでのユーザー検索機能を提供します。
 */
@Service
@RequiredArgsConstructor
public class UserRegistService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 指定されたユーザーIDでユーザーを検索します。
     *
     * @param userId 検索するユーザーのID
     * @return 一致するユーザーのエンティティ
     */
    @Transactional(readOnly = true)
    public Users findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    /**
     * 新規ユーザーを登録します。パスワードの一致確認と重複ユーザーのチェックを行い、登録します。
     *
     * @param userAuthDto 登録するユーザーの情報を持つDTO
     * @throws IllegalArgumentException パスワードが一致しない場合、または既に存在するユーザーの場合にスローされます
     */
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
