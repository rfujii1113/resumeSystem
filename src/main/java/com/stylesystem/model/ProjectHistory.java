package com.stylesystem.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "project_history") 
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true) 
public class ProjectHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include 
    private int projectId;

    @ToString.Include
    private String projectName;

    private LocalDate startDate;
    private LocalDate endDate;
    private String workLocation;

    @ElementCollection
    private Set<String> database;

    @ElementCollection
    private Set<String> hardware;

    @ElementCollection
    private Set<String> language;

    @ElementCollection
    private Set<String> os;

    @ElementCollection
    private Set<String> responsibility;

    @ElementCollection
    private Set<String> tools;

    // Many-to-One Relationship with Employee
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;
}
