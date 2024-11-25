package com.stylesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stylesystem.model.Users;

/**
 * ユーザー情報を管理するリポジトリインターフェース。
 * ユーザーの検索、削除フラグの更新、および有効なユーザーの取得機能を提供します。
 */
public interface UserRepository extends JpaRepository<Users, String> {

    /**
     * 指定されたユーザーIDに一致するユーザーを取得します。
     *
     * @param userId 検索対象のユーザーID
     * @return 一致するユーザー
     */
    Users findByUserId(String userId);

    /**
     * 削除フラグの順に並べられた全ユーザーを取得します。
     *
     * @return 削除フラグ順のユーザーリスト
     */
    List<Users> findAllByOrderByDeleteFlag();

    /**
     * 削除フラグがfalseの全有効ユーザーを取得します。
     *
     * @return 有効なユーザーリスト
     */
    @Query("SELECT u FROM Users u WHERE u.deleteFlag = false")
    List<Users> findAllActiveUsers();

    /**
     * 指定されたユーザーIDの削除フラグを更新します。
     *
     * @param userId 更新対象のユーザーID
     * @param status 新しい削除フラグのステータス
     */
    @Modifying
    @Query("UPDATE Users u SET u.deleteFlag = :status WHERE u.userId = :userId")
    void updateDeleteFlag(@Param("userId") String userId, @Param("status") boolean status);

}
