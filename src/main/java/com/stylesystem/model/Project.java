package com.stylesystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * プロジェクトのエンティティクラス。
 * 各プロジェクトの詳細情報と関連するユーザーを管理します。
 */
@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    /**
     * プロジェクトのユニーク識別子。
     */
    @Id
    @Column(name = "project_id", updatable = false, nullable = false)
    private String projectId;

    /**
     * プロジェクト名。
     */
    private String projectName;

    /**
     * プロジェクトの開始日。
     */
    private LocalDate startDate;

    /**
     * プロジェクトの終了日。
     */
    private LocalDate endDate;

    /**
     * プロジェクトの実施場所。
     */
    private String location;

    /**
     * プロジェクトの種類。
     */
    private String projectType;

    /**
     * プロジェクトでの責任内容。
     */
    private String responsibility;

    /**
     * プロジェクトに関連するユーザー（多対一の関係）。
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    /**
     * プロジェクトで使用されたオペレーティングシステムのリスト。
     */
    @Type(ListArrayType.class)
    @Column(name = "os", columnDefinition = "text[]")
    @Builder.Default
    private List<String> os = new ArrayList<>();

    /**
     * プロジェクトで使用されたハードウェアのリスト。
     */
    @Type(ListArrayType.class)
    @Column(name = "hw", columnDefinition = "text[]")
    @Builder.Default
    private List<String> hw = new ArrayList<>();

    /**
     * プロジェクトで使用されたデータベースのリスト。
     */
    @Type(ListArrayType.class)
    @Column(name = "db", columnDefinition = "text[]")
    @Builder.Default
    private List<String> db = new ArrayList<>();

    /**
     * プロジェクトで使用されたプログラミング言語のリスト。
     */
    @Type(ListArrayType.class)
    @Column(name = "language", columnDefinition = "text[]")
    @Builder.Default
    private List<String> language = new ArrayList<>();

    /**
     * プロジェクトで使用されたツールのリスト。
     */
    @Type(ListArrayType.class)
    @Column(name = "tool", columnDefinition = "text[]")
    @Builder.Default
    private List<String> tool = new ArrayList<>();

    /**
     * プロジェクトで実施されたプロセスのリスト。
     */
    @Type(ListArrayType.class)
    @Column(name = "processes", columnDefinition = "text[]")
    @Builder.Default
    private List<String> processes = new ArrayList<>();
}
