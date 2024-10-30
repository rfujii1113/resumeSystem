package com.stylesystem.repository;

import com.stylesystem.model.SkillMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SkillRepository extends JpaRepository<SkillMaster, Long> {
       List<SkillMaster> findByCategory(String category);
}
