package com.stylesystem.dto;

import com.stylesystem.model.Project;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {

    private String projectId;   // Unique identifier maked uuid
    private String projectName;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // フォーマットを指定
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // フォーマットを指定
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
                .projectId(project.getProjectId())
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
                .projectId(this.projectId)
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
