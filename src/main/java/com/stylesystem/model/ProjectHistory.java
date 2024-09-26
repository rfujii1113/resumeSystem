package com.stylesystem.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ProjectHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;

    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String workLocation;

    @ElementCollection
    @Column(name = "hardware", columnDefinition = "text[]")
    private List<String> hardware;

    @ElementCollection
    @Column(name = "os", columnDefinition = "text[]")
    private List<String> os;

    @ElementCollection
    @Column(name = "database", columnDefinition = "text[]")
    private List<String> database;

    @ElementCollection
    @Column(name = "language", columnDefinition = "text[]")
    private List<String> language;

    @ElementCollection
    @Column(name = "tools", columnDefinition = "text[]")
    private List<String> tools;

    @ElementCollection
    @Column(name = "responsibility", columnDefinition = "text[]")
    private List<String> responsibility;

    // 社員との多対多の関係
    @ManyToOne
    @JoinColumn(name = "empId")
    private Employee employee;
}