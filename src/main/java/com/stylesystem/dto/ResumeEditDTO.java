package com.stylesystem.dto;

import java.time.LocalDate;
import java.util.List;

import com.stylesystem.model.Project;

import lombok.Data;

@Data
public class ResumeEditDTO {
    private String userId;
	private String userName;
	private String gender;
	private LocalDate birthDate;
	private String currentAddress;
	private String permanentAddress;
	private String spouse;
	private String lastSchool;
	private String lastSchoolType;
	private LocalDate graduationDate;
	private String educationCourse;
	private String nearestStation;
	private String selfPr;
	
	private List<Project> projectHistories;
}
