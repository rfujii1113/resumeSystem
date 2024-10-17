package com.stylesystem.service;

import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.UserInfoDto;
import com.stylesystem.dto.ProjectDto;
import com.stylesystem.model.Users;
import com.stylesystem.model.Project;
import com.stylesystem.repository.UserRepository;
import com.stylesystem.repository.ProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        // 현재 사용자 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        // 사용자 엔티티 조회
        Users users = userRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 사용자 정보 업데이트
        resumeDto.getUserInfo().updateEntity(users);
        userRepository.save(users);

        // 기존 프로젝트 삭제 후 새 프로젝트 저장
        projectRepository.deleteByUsers(users);

        for (ProjectDto projectDto : resumeDto.getProjects()) {
            Project project = projectDto.toEntity();
            project.setUsers(users); 
            projectRepository.save(project);
        }
    }

    @Transactional(readOnly = true)
    public ResumeDto getResumeForCurrentUser() {
        // 현재 사용자 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        // 사용자 엔티티 조회
        Users users = userRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 사용자 정보 DTO 변환
        UserInfoDto userInfoDto = UserInfoDto.fromEntity(users);

        // 프로젝트 엔티티 조회 및 DTO 변환
        List<ProjectDto> projectDtos = projectRepository.findByUsers(users).stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());

        // ResumeDto 생성
        ResumeDto resumeDto = ResumeDto.builder()
                .userInfo(userInfoDto)
                .projects(projectDtos)
                .build();
                
        return resumeDto;
    }
}

