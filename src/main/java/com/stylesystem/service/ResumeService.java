package com.stylesystem.service;

import com.stylesystem.dto.ResumeDto;
import com.stylesystem.model.Project;
import com.stylesystem.model.Skill;
import com.stylesystem.model.Users;
import com.stylesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository; // 스킬 저장을 위한 리포지토리 추가

    @Transactional
    public void saveResume(ResumeDto resumeDto) {
        // Users 엔티티 생성
        Users user = Users.builder()
                .userId(generateUserId()) // 유저 ID 생성 로직 필요
                .userName(resumeDto.getUserName())
                .userNameRomaji(resumeDto.getUserNameRomaji())
                .email(resumeDto.getEmail())
                .gender(resumeDto.isGender())
                .birthDate(resumeDto.getBirthDate())
                .currentAddress(resumeDto.getCurrentAddress())
                .permanentAddress("") // 필요 시 추가 입력
                .spouse(false) // 필요 시 수정
                .lastSchool(resumeDto.getLastSchool())
                .lastSchoolType(resumeDto.getLastSchoolType())
                .major(resumeDto.getMajor())
                .graduationDate(resumeDto.getGraduationDate())
                .educationCourse("") // 필요 시 추가 입력
                .nearestStation("") // 필요 시 추가 입력
                .role("USER") // 기본 역할 설정
                .password("") // 필요 시 암호 설정
                .deleteFlag(false)
                .selfPr(resumeDto.getSelfPr())
                .projects(new ArrayList<>())
                .build();

        // Projects 엔티티 생성 및 연결
        if (resumeDto.getProjects() != null) {
            for (ResumeDto.ProjectDto projectDto : resumeDto.getProjects()) {
                Project project = Project.builder()
                        .projectName(projectDto.getProjectName())
                        .location(projectDto.getLocation())
                        .startDate(projectDto.getStartDate())
                        .endDate(projectDto.getEndDate())
                        .projectType("") // 필요 시 추가 입력
                        .users(user)
                        .skills(new ArrayList<>())
                        .build();

                // Processes는 별도의 엔티티로 저장하거나 문자열로 저장
                // 여기서는 단순히 문자열로 저장한다고 가정
                // 필요 시 Process 엔티티와의 관계 설정

                // Skills 연결
                if (projectDto.getSkills() != null) {
                    for (String skillName : projectDto.getSkills()) {
                        Skill skill = skillRepository.findBySkillName(skillName)
                                .orElseGet(() -> Skill.builder()
                                        .skillName(skillName)
                                        .skillCategory(determineCategory(skillName))
                                        .build());
                        project.getSkills().add(skill);
                    }
                }

                user.getProjects().add(project);
            }
        }

        userRepository.save(user);
    }

    private String generateUserId() {
        // 유니크한 유저 ID 생성 로직 구현
        // 예: UUID 사용
        return java.util.UUID.randomUUID().toString();
    }

    private String determineCategory(String skillName) {
        // 스킬 이름을 기반으로 카테고리 결정 로직 구현
        // 예시:
        if (List.of("JavaScript", "Python", "Java").contains(skillName)) {
            return "Language";
        } else if (List.of("MySQL", "PostgreSQL", "MongoDB").contains(skillName)) {
            return "DB";
        } else if (List.of("Linux", "Windows", "macOS").contains(skillName)) {
            return "OS";
        } else if (List.of("Docker", "Kubernetes", "Git").contains(skillName)) {
            return "Tools";
        }
        return "Other";
    }

    public List<Users> getAllActiveUsers() {
        return userRepository.findAllActiveUsers();
    }
}
