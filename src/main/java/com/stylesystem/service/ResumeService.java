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

/**
 * ユーザーの経歴書（履歴書）を管理するサービスクラス。
 * ユーザー情報の保存、更新、取得機能を提供します。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ResumeRepository resumeRepository;

    /**
     * 指定された経歴書DTOを保存します。
     * ユーザー情報の更新とプロジェクトの追加・削除を行います。
     *
     * @param resumeDto 保存する経歴書のDTO
     * @throws IllegalArgumentException ユーザーIDやプロジェクトIDが無効な場合にスローされます
     */
    @Transactional
    public void saveResume(ResumeDto resumeDto) {

        // 経歴書DTOからユーザーIDを取得
        String userId = resumeDto.getUserInfo().getUserId();
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("userId is required");
        }

        // ユーザーエンティティを取得
        Users users = userRepository.findByUserId(userId);
        if (users == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // ユーザー情報を更新
        resumeDto.getUserInfo().updateEntity(users);
        userRepository.save(users);

        // ユーザーに関連するプロジェクトを削除
        projectRepository.deleteByUsers(users);

        // ユーザーに関連する新しいプロジェクトを保存
        for (ProjectDto projectDto : resumeDto.getProjects()) {
            Project project = projectDto.toEntity();

            if (project.getProjectId() == null || project.getProjectId().isEmpty()) {
                throw new IllegalArgumentException("projectId is required for project: " + project.getProjectName());
            }

            project.setUsers(users);
            projectRepository.save(project);
        }
    }

    /**
     * 指定されたユーザーIDに基づいて経歴書を取得します。
     *
     * @param userId 経歴書を取得するユーザーID
     * @return 指定されたユーザーIDに関連する経歴書DTO、存在しない場合はnull
     */
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
