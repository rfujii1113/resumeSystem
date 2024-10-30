package com.stylesystem.repository;

import com.stylesystem.model.Users;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Users, String> {

    @EntityGraph(attributePaths = "projects")
    Optional<Users> findByUserId(String userId);
}
