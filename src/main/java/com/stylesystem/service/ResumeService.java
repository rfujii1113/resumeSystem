package com.stylesystem.service;

import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.ProjectDto;
import com.stylesystem.model.Users;
import com.stylesystem.model.Project;
import com.stylesystem.model.Skill;
import com.stylesystem.repository.UserRepository;
import com.stylesystem.repository.ProjectRepository;
import com.stylesystem.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    @Transactional
    public void saveResume(ResumeDto resumeDto) {
        // 1. convert UserInfoDto to User entity and save
        Users user = resumeDto.getUserInfo().toEntity();
        userRepository.save(user);

        // 2. convert ProjectDto to Project entity and save
        for (ProjectDto projectDto : resumeDto.getProjects()) {
            Project project = projectDto.toEntity();
            project.setUsers(user); 

            // if skills are not empty, save them
            if (projectDto.getSkills() != null && !projectDto.getSkills().isEmpty()) {
                Set<Skill> skillSet = new HashSet<>();
                for (String skillName : projectDto.getSkills()) {
                    Skill skill = skillRepository.findBySkillName(skillName);
                    if (skill == null) {
                        // if skill does not exist, create a new skill
                        skill = Skill.builder()
                                .skillName(skillName)
                                .build();
                        skillRepository.save(skill);
                    }
                    skillSet.add(skill);
                }
                project.setSkills(new ArrayList<>(skillSet));
            }

            projectRepository.save(project);
        }
    }
}
