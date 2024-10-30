package com.stylesystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

import com.stylesystem.model.SkillMaster;

import java.util.ArrayList; 

/**
 * 経歴書（レジュメ）のデータ転送オブジェクト（DTO）。
 * ユーザー情報、プロジェクト、スキル情報を含みます。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {

    /**
     * ユーザーの基本情報を保持するフィールド。
     */
    private UserInfoDto userInfo;
    
    /**
     * 経歴書に含まれるプロジェクトのリスト。
     * プロジェクトがない場合は空のリストが設定されます。
     */
    @Builder.Default
    private List<ProjectDto> projects = new ArrayList<>(); 

    /**
     * 経歴書に含まれるスキルのリスト。
     * スキルがない場合は空のリストが設定されます。
     */
    @Builder.Default
    private List<SkillMaster> skillMasters = new ArrayList<>();
}
