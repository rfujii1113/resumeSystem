package com.stylesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {

    // Personal Information
    private String userName;
    private String userNameRomaji;
    private String email;
    private boolean gender;
    private LocalDate birthDate;
    private String nationality;
    private String currentAddress;

    // Education
    private String lastSchool;
    private String lastSchoolType;
    private String major;
    private LocalDate graduationDate;

    // Projects
    private List<ProjectDto> projects;

    // Summary
    private String selfPr;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectDto {
        private String projectName;
        private String location;
        private LocalDate startDate;
        private LocalDate endDate;
        private List<String> processes;
        private List<String> skills;
        private String responsibility;
    }
}
