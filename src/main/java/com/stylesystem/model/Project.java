package com.stylesystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private String responsibility;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    private Skill skills; 

    @Type(ListArrayType.class)
    @Column(name = "processes", columnDefinition = "text[]")
    @Builder.Default
    private List<String> processes = new ArrayList<>();
}

