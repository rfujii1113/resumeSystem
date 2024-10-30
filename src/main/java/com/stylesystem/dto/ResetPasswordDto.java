package com.stylesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * パスワードリセット用のデータ転送オブジェクト（DTO）。
 * 新しいパスワードと確認用パスワードを保持し、ユーザーIDで紐づけます。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordDto {

    /**
     * パスワードをリセットする対象のユーザーID。
     */
    private String userId;

    /**
     * 新しいパスワード。
     */
    private String newPassword;

    /**
     * 新しいパスワードの確認用フィールド。
     * newPasswordと一致する必要があります。
     */
    private String confirmPassword;
}
