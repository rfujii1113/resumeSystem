package com.stylesystem.model;

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
    private Long id;

    private String os;
    private String db;
    private String language;
    private String tool;
}
