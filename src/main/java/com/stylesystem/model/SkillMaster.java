package com.stylesystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skill_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category; 
}