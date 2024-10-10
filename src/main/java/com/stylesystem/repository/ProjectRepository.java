package com.stylesystem.repository;

import com.stylesystem.model.Project;
import com.stylesystem.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    void deleteByUsers(Users users);
}

