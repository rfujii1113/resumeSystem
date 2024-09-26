package com.stylesystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    @Column(name = "database", columnDefinition = "text[]")
    private List<String> database;

    @Column(name = "hardware", columnDefinition = "text[]")
    private List<String> hardware;

    @Column(name = "language", columnDefinition = "text[]")
    private List<String> language;

    @Column(name = "os", columnDefinition = "text[]")
    private List<String> os;

    @Column(name = "responsibility", columnDefinition = "text[]")
    private List<String> responsibility;

    @Column(name = "tools", columnDefinition = "text[]")
    private List<String> tools;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;
}
