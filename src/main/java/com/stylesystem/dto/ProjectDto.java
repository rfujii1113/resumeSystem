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
    private List<String> skills;
    private String responsibility;

    // Convert Project to ProjectDto
    public static ProjectDto fromEntity(Project project) {
        return ProjectDto.builder()
            .projectName(project.getProjectName())
            .location(project.getLocation())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .processes(project.getProcesses())
            .responsibility(project.getResponsibility())
            .build();
    }

    // Convert ProjectDto to Project
    public Project toEntity() {
        Project project = new Project();
        project.setProjectName(this.projectName);
        project.setLocation(this.location);
        project.setStartDate(this.startDate);
        project.setEndDate(this.endDate);
        project.setProcesses(this.processes);
        project.setResponsibility(this.responsibility);
        return project;
    }
}
