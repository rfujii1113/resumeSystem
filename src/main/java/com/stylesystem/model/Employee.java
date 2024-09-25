package com.stylesystem.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;

    private String empName;
    private String gender;
    private LocalDate birthDate;
    private String currentAddress;
    private String permanentAddress;
    private String lastSchool;
    private String major;
    private LocalDate graduationDate;
    private String nearestStation;
    private String department;
    private Integer deleteFlag;
    private Integer permission;
    
    // プロジェクト履歴との一対多関係
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<ProjectHistory> projectHistories;

    //スキルとの多対多の関係
    @ManyToMany
    @JoinTable(
        name = "EmployeeSkill",
        joinColumns = @JoinColumn(name = "empId"),
        inverseJoinColumns = @JoinColumn(name = "skillId")
    )
    private List<Skill> skills;
}