package com.stylesystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "process")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long processId;

    private String processType;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}