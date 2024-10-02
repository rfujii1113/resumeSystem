package com.stylesystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;
    private String gender;
    private LocalDate birthDate;
    private String currentAddress;
    private String permanentAddress;
    private String spouse;
    private String lastSchool;
    private String lastSchoolType;
    private String major;
    private LocalDate graduationDate;
    private String educationCourse;
    private String nearestStation;
    private String role;
    private String password;
    private boolean deleteFlag;
    private String selfPr;

    @OneToMany(mappedBy = "users")
    private List<Project> projects;
}