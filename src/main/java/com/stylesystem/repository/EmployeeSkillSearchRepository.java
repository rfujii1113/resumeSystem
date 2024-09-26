package com.stylesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stylesystem.model.Skill;

public interface EmployeeSkillSearchRepository  extends JpaRepository<Skill,Integer>{

}
