package com.stylesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.stylesystem.model.Users;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Users, String> {

    Users findByUserId(String userId);

    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.projects WHERE u.userId = :userId")
    Optional<Users> findByUserIdWithProjects(@Param("userId") String userId);
}