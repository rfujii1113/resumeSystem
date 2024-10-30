package com.stylesystem.service;

import com.stylesystem.dto.ProjectDto;
import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.UserInfoDto;
import com.stylesystem.model.Project;
import com.stylesystem.model.Users;
import com.stylesystem.repository.ProjectRepository;
import com.stylesystem.repository.ResumeRepository;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ResumeRepository resumeRepository;

    @Transactional
    public void saveResume(ResumeDto resumeDto) {

        // Get the user ID from the resume DTO
        String userId = resumeDto.getUserInfo().getUserId();
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("userId is required");
        }

        // Get the user entity for the current user
        Users users = userRepository.findByUserId(userId);
        if (users == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Update the user info
        resumeDto.getUserInfo().updateEntity(users);
        userRepository.save(users);

        // Delete the projects for the user
        projectRepository.deleteByUsers(users);

        // Save the projects for the user
        for (ProjectDto projectDto : resumeDto.getProjects()) {
            Project project = projectDto.toEntity();

            if (project.getProjectId() == null || project.getProjectId().isEmpty()) {
                throw new IllegalArgumentException("projectId is required for project: " + project.getProjectName());
            }

            project.setUsers(users);
            projectRepository.save(project);
        }
    }

    @Transactional(readOnly = true)
    public ResumeDto getResumeByUserId(String userId) {
        Users user = resumeRepository.findByUserId(userId).orElse(null);

        if (user == null) {
            return null;
        }

        UserInfoDto userInfoDto = UserInfoDto.fromEntity(user);

        List<ProjectDto> projectDtos = user.getProjects().stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());

        ResumeDto resumeDto = ResumeDto.builder()
                .userInfo(userInfoDto)
                .projects(projectDtos)
                .build();

        return resumeDto;
    }
}
