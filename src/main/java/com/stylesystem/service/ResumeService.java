package com.stylesystem.service;

import com.stylesystem.dto.ProjectDto;
import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.UserInfoDto;
import com.stylesystem.model.Project;
import com.stylesystem.model.SkillMaster;
import com.stylesystem.model.Users;
import com.stylesystem.repository.ProjectRepository;
import com.stylesystem.repository.ResumeRepository;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ResumeRepository resumeRepository;
    private final SkillMasterService skillMasterService;

    @Transactional
    public void saveResume(ResumeDto resumeDto) {

         // 로그 추가: 전달받은 ResumeDto 확인
         log.debug("Saving Resume for User ID: {}", resumeDto.getUserInfo().getUserId());
         log.debug("Projects to save: {}", resumeDto.getProjects());
         log.debug("Skills to save: {}", resumeDto.getSkillMasters());

        String userId = resumeDto.getUserInfo().getUserId();
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("userId is required");
        }

        Users users = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Update the user info
        resumeDto.getUserInfo().updateEntity(users);
        userRepository.save(users);

        // Delete the projects for the user
        projectRepository.deleteByUsers(users);

        // Save the projects for the user
        for (ProjectDto projectDto : resumeDto.getProjects()) {
            Project project = projectDto.toEntity();

            // projectId가 존재하지 않으면 예외를 던짐
            if (project.getProjectId() == null || project.getProjectId().isEmpty()) {
                throw new IllegalArgumentException("projectId is required for project: " + project.getProjectName());
            }

            project.setUsers(users);
            projectRepository.save(project); // projectId를 수동으로 설정한 후 저장
        }
    }

    @Transactional(readOnly = true)
    public ResumeDto getResumeForCurrentUser() {

        // Get the current user's ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        // Get the user entity for the current user
        Users users = userRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert the user entity to a DTO
        UserInfoDto userInfoDto = UserInfoDto.fromEntity(users);

        // Get the list of projects for the user
        List<ProjectDto> projectDtos = projectRepository.findByUsers(users).stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());

        // If the list of projects is empty and the user info is null, return null
        if (projectDtos.isEmpty() && (userInfoDto == null || userInfoDto.getUserName() == null)) {
            return null;
        }

        // Create a new resume DTO with the user info and projects
        ResumeDto resumeDto = ResumeDto.builder()
                .userInfo(userInfoDto)
                .projects(projectDtos)
                .build();

        return resumeDto;
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

    @Transactional(readOnly = true)
    public Map<String, List<SkillMaster>> getSkillsByCategory() {
        List<SkillMaster> allSkills = skillMasterService.getAllSkills();
        return allSkills.stream().collect(Collectors.groupingBy(SkillMaster::getCategory));
    }
}
