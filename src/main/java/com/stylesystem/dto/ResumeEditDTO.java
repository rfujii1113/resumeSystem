package com.stylesystem.dto;

import java.time.LocalDate;
import java.util.List;

import com.stylesystem.model.Project;

import lombok.Data;

/**
 * Exexl Export機能で使用されるデータ転送オブジェクト（DTO）。
 * ユーザーの基本情報とプロジェクト履歴を保持します。
 */
@Data
public class ResumeEditDto {

    /**
     * ユーザーのID。
     */
    private String userId;

    /**
     * ユーザーの名前。
     */
    private String userName;

    /**
     * ユーザーの性別（男性: true, 女性: false）。
     */
    private Boolean gender;

    /**
     * ユーザーの生年月日。
     */
    private LocalDate birthDate;

    /**
     * 現住所。
     */
    private String currentAddress;

    /**
     * 本籍地（永久住所）。
     */
    private String permanentAddress;

    /**
     * 配偶者の有無（あり: true, なし: false）。
     */
    private Boolean spouse;

    /**
     * 最終学歴の学校名。
     */
    private String lastSchool;

    /**
     * 最終学歴の学校の種類（例: 大学、専門学校など）。
     */
    private String lastSchoolType;

    /**
     * 卒業日。
     */
    private LocalDate graduationDate;

    /**
     * 専攻または学習コース。
     */
    private String educationCourse;

    /**
     * 最寄り駅。
     */
    private String nearestStation;

    /**
     * 自己PR。
     */
    private String selfPr;

    /**
     * プロジェクト履歴のリスト。
     */
    private List<Project> projectHistories;
}
