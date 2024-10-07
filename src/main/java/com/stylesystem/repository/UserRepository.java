package com.stylesystem.repository;

import com.stylesystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUserId(String userId);

    @Query("SELECT u FROM Users u WHERE u.deleteFlag = false")
    List<Users> findAllActiveUsers();

    // user delete flag update
    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.deleteFlag = :status WHERE u.userId = :userId")
    void updateDeleteFlag(String userId, boolean status);
}
