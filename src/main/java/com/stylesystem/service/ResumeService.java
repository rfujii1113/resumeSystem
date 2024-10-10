package com.stylesystem.service;

import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.ProjectDto;
import com.stylesystem.model.Users;
import com.stylesystem.model.Project;
import com.stylesystem.repository.UserRepository;
import com.stylesystem.repository.ProjectRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void saveResume(ResumeDto resumeDto) {
        // get current user id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        // 1. convert UserInfoDto to User entity and save
        Users users = userRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        resumeDto.getUserInfo().updateEntity(users);
        userRepository.save(users);

        // 2. delete all projects of the user, then save new projects
        projectRepository.deleteByUsers(users);

        for (ProjectDto projectDto : resumeDto.getProjects()) {
            Project project = projectDto.toEntity();
            project.setUsers(users); 
            projectRepository.save(project);
        }
    }
}
