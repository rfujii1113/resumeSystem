package com.stylesystem.repository;

import com.stylesystem.model.Project;
import com.stylesystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUsers(Users users);
    
    void deleteByUsers(Users users);

    List<Project> findByUsers_UserId(String userId);
}
