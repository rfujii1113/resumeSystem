package com.stylesystem.repository;

import com.stylesystem.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // if you need to add custom query methods, add them here
}
