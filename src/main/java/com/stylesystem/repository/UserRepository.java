package com.stylesystem.repository;

import com.stylesystem.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUserId(String userId);

    List<Users> findAllByOrderByDeleteFlag();

    // user delete flag update
    @Query("SELECT u FROM Users u WHERE u.deleteFlag = false")
    List<Users> findAllActiveUsers();

    // user delete flag update
    @Modifying
    @Query("UPDATE Users u SET u.deleteFlag = :status WHERE u.userId = :userId")
    void updateDeleteFlag(@Param("userId") String userId, @Param("status") boolean status);
}
