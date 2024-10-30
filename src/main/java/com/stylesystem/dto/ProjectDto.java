package com.stylesystem.dto;

import com.stylesystem.model.Project;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * プロジェクトのデータ転送オブジェクト（DTO）。
 * プロジェクト情報を表し、EntityとDTO間の変換メソッドを提供します。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {

    /**
     * プロジェクトのユニーク識別子 (UUIDで生成)。
     */
    private String projectId;

    /**
     * プロジェクト名。
     */
    private String projectName;

    /**
     * プロジェクトの場所。
     */
    private String location;

    /**
     * プロジェクトの開始日。フォーマットは「yyyy-MM-dd」です。
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * プロジェクトの終了日。フォーマットは「yyyy-MM-dd」です。
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * プロジェクトで実施されたプロセスのリスト。
     */
    @Builder.Default
    private List<String> processes = new ArrayList<>();

    /**
     * 使用されたオペレーティングシステム(OS)のリスト。
     */
    @Builder.Default
    private List<String> os = new ArrayList<>();

    /**
     * 使用されたハードウェア(HW)のリスト。
     */
    @Builder.Default
    private List<String> hw = new ArrayList<>();

    /**
     * 使用されたデータベース(DB)のリスト。
     */
    @Builder.Default
    private List<String> db = new ArrayList<>();

    /**
     * 使用されたプログラミング言語のリスト。
     */
    @Builder.Default
    private List<String> language = new ArrayList<>();

    /**
     * 使用されたツールのリスト。
     */
    @Builder.Default
    private List<String> tool = new ArrayList<>();

    /**
     * プロジェクトでの責任内容。
     */
    private String responsibility;

    /**
     * ProjectエンティティからProjectDtoへの変換を行うメソッド。
     *
     * @param project 変換するProjectエンティティ
     * @return Projectエンティティに対応するProjectDto
     */
    public static ProjectDto fromEntity(Project project) {
        return ProjectDto.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .location(project.getLocation())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .processes(project.getProcesses())
                .os(project.getOs())
                .db(project.getDb())
                .language(project.getLanguage())
                .tool(project.getTool())
                .responsibility(project.getResponsibility())
                .build();
    }

    /**
     * ProjectDtoをProjectエンティティに変換するメソッド。
     *
     * @return Projectエンティティに変換されたインスタンス
     */
    public Project toEntity() {
        return Project.builder()
                .projectId(this.projectId)
                .projectName(this.projectName)
                .location(this.location)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .processes(this.processes)
                .os(this.os)
                .db(this.db)
                .language(this.language)
                .tool(this.tool)
                .responsibility(this.responsibility)
                .build();
    }
}
