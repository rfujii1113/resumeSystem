package com.stylesystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

import com.stylesystem.model.Project;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {

    private String projectName;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> processes;
    private SkillDto skills; 
    private String responsibility;

    // Convert Project to ProjectDto
    public static ProjectDto fromEntity(Project project) {
        return ProjectDto.builder()
            .projectName(project.getProjectName())
            .location(project.getLocation())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .processes(project.getProcesses())
            .skills(SkillDto.fromEntity(project.getSkills()))
            .responsibility(project.getResponsibility())
            .build();
    }

    // Convert ProjectDto to Project
    public Project toEntity() {
        return Project.builder()
                .projectName(this.projectName)
                .location(this.location)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .processes(this.processes)
                .skills(skills.toEntity())
                .responsibility(this.responsibility)
                .build();
    }
}
