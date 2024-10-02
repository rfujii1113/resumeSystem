package com.stylesystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skill_project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}