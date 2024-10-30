package com.stylesystem.repository;

import com.stylesystem.model.Users;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ユーザーの経歴書（プロジェクト情報を含む）を管理するリポジトリインターフェース。
 * プロジェクト情報を取得する際、N+1問題を回避するために@EntityGraphを使用します。
 */
@Repository
public interface ResumeRepository extends JpaRepository<Users, String> {

    /**
     * 指定されたユーザーIDに関連するユーザー情報とプロジェクト情報を取得します。
     * プロジェクト情報も同時にフェッチされます。
     *
     * @param userId 検索対象のユーザーID
     * @return 指定されたユーザーIDに一致するユーザーのOptional
     */
    @EntityGraph(attributePaths = "projects")
    Optional<Users> findByUserId(String userId);
}
