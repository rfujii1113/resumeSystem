package com.stylesystem.model;

import java.time.LocalDate;
import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import jakarta.persistence.Table;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "employee")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Employee {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    private int empId;

    @ToString.Include
    private String empName;
    
    private String password;
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

    // One-to-Many Relationship with ProjectHistory
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectHistory> projectHistories;

    // Many-to-Many Relationship with Skill
    @ManyToMany
    @JoinTable(
        name = "employee_skill",
        joinColumns = @JoinColumn(name = "emp_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;
}
