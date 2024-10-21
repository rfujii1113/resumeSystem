package com.stylesystem.repository;

import com.stylesystem.model.Project;
import com.stylesystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // 追加
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUsers(Users users);
    
    void deleteByUsers(Users users);

    List<Project> findByUsers_UserId(String userId);

    // プロジェクト開始日が新しい順DESC
    List<Project> findByUsers_UserIdOrderByStartDateAsc(String userId);
    
    // Language重複無し
    @Query(value = "SELECT DISTINCT unnest(p.language) FROM project p WHERE p.user_id = :userId", nativeQuery = true)
    Set<String> findDistinctLanguagesByUserId(@Param("userId") String userId); // 修正
    
    // OS重複なし
    @Query(value = "SELECT DISTINCT unnest(p.os) FROM project p WHERE p.user_id = :userId", nativeQuery = true)
    Set<String> findDistinctOSByUserId(@Param("userId") String userId); // 修正
    
    // DB重複無し
    @Query(value = "SELECT DISTINCT unnest(p.db) FROM project p WHERE p.user_id = :userId", nativeQuery = true)
    Set<String> findDistinctDBByUserId(@Param("userId") String userId); // 修正
    
    // tool重複無し
    @Query(value = "SELECT DISTINCT unnest(p.tool) FROM project p WHERE p.user_id = :userId", nativeQuery = true)
    Set<String> findDistinctToolByUserId(@Param("userId") String userId); // 修正
}
