package com.stylesystem.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skill")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    private String skillName;
    private String skillCategory;

    @ManyToMany(mappedBy = "skills")
    private List<Project> projects;
}