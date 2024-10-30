package com.stylesystem.service;

import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ユーザーのアクティブ・非アクティブ状態を管理するサービスクラス。
 * ユーザーの削除フラグの更新機能と、アクティブなユーザーの取得機能を提供します。
 */
@Service
@RequiredArgsConstructor
public class UserDeleteService {
    
    private final UserRepository userRepository;

    /**
     * 全アクティブユーザーを取得します。
     *
     * @return 削除フラグがfalseの全ユーザーリスト
     */
    @Transactional(readOnly = true)
    public List<Users> getAllActiveUsers() {
        return userRepository.findAllActiveUsers();
    }

    /**
     * 指定されたユーザーを非アクティブ状態に設定します（削除フラグをtrueに更新）。
     *
     * @param userId 非アクティブにするユーザーのID
     */
    @Transactional
    public void deactivateUser(String userId) {
        userRepository.updateDeleteFlag(userId, true);
    }

    /**
     * 指定されたユーザーをアクティブ状態に設定します（削除フラグをfalseに更新）。
     *
     * @param userId アクティブにするユーザーのID
     */
    @Transactional
    public void activateUser(String userId) {
        userRepository.updateDeleteFlag(userId, false);
    }
}
