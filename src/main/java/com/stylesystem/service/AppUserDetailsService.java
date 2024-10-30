package com.stylesystem.service;

import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;

/**
 * Spring Securityのログイン機能を実現するためのユーザー詳細サービスクラス。
 * ユーザーIDに基づいてユーザー情報を取得し、認証に必要なユーザー情報を提供します。
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 指定されたユーザーIDでユーザー情報をロードし、認証に使用するユーザー詳細を返します。
     *
     * @param userId 認証対象のユーザーID
     * @return ユーザーIDに対応するUserDetailsオブジェクト
     * @throws UsernameNotFoundException ユーザーが見つからない場合にスローされます
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = userRepository.findByUserId(userId);
    
        if (user == null) {
            throw new UsernameNotFoundException(userId + " not found.");
        }
    
        return new AppUserDetails(user);
    }

    /**
     * ユーザーの認証情報を管理する内部クラス。
     * ユーザーの権限、パスワード、状態情報を提供します。
     */
    @RequiredArgsConstructor
    private static class AppUserDetails implements UserDetails {

        private final Users users;

        /**
         * ユーザーの権限を返します。
         *
         * @return 権限のリスト
         */
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(users.getRole()));
        }

        /**
         * ユーザーのパスワードを返します。
         *
         * @return ユーザーのパスワード
         */
        @Override
        public String getPassword() {
            return users.getPassword();
        }

        /**
         * ユーザーのユーザーIDを返します。
         *
         * @return ユーザーのユーザーID
         */
        @Override
        public String getUsername() {
            return users.getUserId();
        }

        /**
         * アカウントの有効期限が切れていないかを示します。
         *
         * @return 常にtrue（期限切れなし）
         */
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        /**
         * アカウントがロックされていないかを示します。
         *
         * @return 常にtrue（ロックなし）
         */
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        /**
         * 認証資格情報の有効期限が切れていないかを示します。
         *
         * @return 常にtrue（期限切れなし）
         */
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        /**
         * アカウントが有効かどうかを示します。削除フラグがfalseの場合のみ有効です。
         *
         * @return アカウントが有効な場合はtrue、削除フラグがtrueの場合はfalse
         */
        @Override
        public boolean isEnabled() {
            return !users.getDeleteFlag();
        }
    }
}
