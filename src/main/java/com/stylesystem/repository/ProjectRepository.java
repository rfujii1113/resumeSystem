package com.stylesystem.repository;

import com.stylesystem.model.Project;
import com.stylesystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * プロジェクト情報を管理するリポジトリインターフェース。
 * プロジェクトのCRUD操作とカスタムクエリメソッドを提供します。
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    /**
     * 指定されたユーザーに関連するプロジェクトのリストを取得します。
     *
     * @param users 取得対象のユーザー
     * @return ユーザーに関連するプロジェクトのリスト
     */
    List<Project> findByUsers(Users users);
    
    /**
     * 指定されたユーザーに関連するプロジェクトを削除します。
     *
     * @param users 削除対象のユーザー
     */
    void deleteByUsers(Users users);

    /**
     * 指定されたユーザーIDに関連するプロジェクトのリストを取得します。
     *
     * @param userId ユーザーID
     * @return ユーザーIDに関連するプロジェクトのリスト
     */
    List<Project> findByUsers_UserId(String userId);

    /**
     * 指定されたユーザーIDに関連するプロジェクトを開始日の昇順で取得します。
     *
     * @param userId ユーザーID
     * @return プロジェクト開始日の昇順でソートされたプロジェクトのリスト
     */
    List<Project> findByUsers_UserIdOrderByStartDateAsc(String userId);

    List<Project> findByUsers_UserIdOrderByStartDateDesc(String userId);
    
    
    /**
     * 指定されたユーザーIDに関連するプロジェクトから重複なしのプログラミング言語を取得します。
     *
     * @param userId ユーザーID
     * @return 重複なしのプログラミング言語のセット
     */
    @Query(value = "SELECT DISTINCT unnest(p.language) FROM project p WHERE p.user_id = :userId", nativeQuery = true)
    Set<String> findDistinctLanguagesByUserId(@Param("userId") String userId);
    
    /**
     * 指定されたユーザーIDに関連するプロジェクトから重複なしのOSを取得します。
     *
     * @param userId ユーザーID
     * @return 重複なしのOSのセット
     */
    @Query(value = "SELECT DISTINCT unnest(p.os) FROM project p WHERE p.user_id = :userId", nativeQuery = true)
    Set<String> findDistinctOSByUserId(@Param("userId") String userId);
    
    /**
     * 指定されたユーザーIDに関連するプロジェクトから重複なしのデータベース(DB)を取得します。
     *
     * @param userId ユーザーID
     * @return 重複なしのデータベースのセット
     */
    @Query(value = "SELECT DISTINCT unnest(p.db) FROM project p WHERE p.user_id = :userId", nativeQuery = true)
    Set<String> findDistinctDBByUserId(@Param("userId") String userId);
    
    /**
     * 指定されたユーザーIDに関連するプロジェクトから重複なしのツールを取得します。
     *
     * @param userId ユーザーID
     * @return 重複なしのツールのセット
     */
    @Query(value = "SELECT DISTINCT unnest(p.tool) FROM project p WHERE p.user_id = :userId", nativeQuery = true)
    Set<String> findDistinctToolByUserId(@Param("userId") String userId);
}
