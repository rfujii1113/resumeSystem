package com.stylesystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String projectType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToMany
    @JoinTable(
            name = "skill_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;
}