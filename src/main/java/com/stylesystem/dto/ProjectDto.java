package com.stylesystem.dto;

import com.stylesystem.model.Project;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {

    private String projectName;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    @Builder.Default
    private List<String> processes = new ArrayList<>();
    @Builder.Default
    private List<String> os = new ArrayList<>();
    @Builder.Default
    private List<String> hw = new ArrayList<>();
    @Builder.Default
    private List<String> db = new ArrayList<>();
    @Builder.Default
    private List<String> language = new ArrayList<>();
    @Builder.Default
    private List<String> tool = new ArrayList<>();
    private String responsibility;

    // Convert Project to ProjectDto
    public static ProjectDto fromEntity(Project project) {
        return ProjectDto.builder()
                .projectName(project.getProjectName())
                .location(project.getLocation())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .processes(project.getProcesses())
                .os(project.getOs())
                .db(project.getDb())
                .language(project.getLanguage())
                .tool(project.getTool())
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
                .os(this.os)
                .db(this.db)
                .language(this.language)
                .tool(this.tool)
                .responsibility(this.responsibility)
                .build();
    }
}
