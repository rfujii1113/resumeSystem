package com.stylesystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * ユーザーの基本情報と関連プロジェクトを管理するエンティティクラス。
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    /**
     * ユーザーのユニークID。
     */
    @Id
    private String userId;

    /**
     * ユーザーの役割（例：ADMIN、USER）。
     */
    private String role;

    /**
     * ユーザーのパスワード（ハッシュ化されたもの）。
     */
    private String password;

    /**
     * 削除フラグ。ユーザーが削除されているかどうかを示します。
     */
    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    /**
     * ユーザーの名前。
     */
    private String userName;

    /**
     * ユーザーのローマ字表記の名前。
     */
    private String userNameRomaji;

    /**
     * ユーザーの国籍。
     */
    private String nationality;

    /**
     * ユーザーのメールアドレス。
     */
    private String email;

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
     * 永住住所。
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
     * 専攻または学科。
     */
    private String major;

    /**
     * 卒業日。
     */
    private LocalDate graduationDate;

    /**
     * 学歴課程またはコース。
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
     * ユーザーに関連するプロジェクトのリスト（1対多の関係）。
     * ユーザーが削除されると、関連するプロジェクトも削除されます。
     */
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;
}
