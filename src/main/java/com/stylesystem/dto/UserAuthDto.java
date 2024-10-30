package com.stylesystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * ユーザー認証情報を表すデータ転送オブジェクト（DTO）。
 * ユーザーID、パスワード、ロール、メールアドレスを含みます。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthDto {

    /**
     * ユーザーのユニークID。
     */
    private String userId;

    /**
     * ユーザーのパスワード。
     */
    private String password;

    /**
     * 確認用パスワード。passwordと一致する必要があります。
     */
    private String confirmPassword;

    /**
     * ユーザーの役割（例：ADMIN、USER）。
     */
    private String role;

    /**
     * ユーザーのメールアドレス。
     */
    private String email;
}
