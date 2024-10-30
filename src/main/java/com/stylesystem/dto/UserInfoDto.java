package com.stylesystem.dto;

import com.stylesystem.model.Users;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * ユーザーの基本情報を保持するデータ転送オブジェクト（DTO）。
 * ユーザーの個人情報、学歴、住所情報を含みます。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {

    /**
     * ユーザーのユニークID。
     */
    private String userId;

    /**
     * ユーザーの名前。
     */
    private String userName;

    /**
     * ユーザーのローマ字表記の名前。
     */
    private String userNameRomaji;

    /**
     * ユーザーのメールアドレス。
     */
    private String email;

    /**
     * ユーザーの性別（男性: true, 女性: false）。
     */
    private Boolean gender;

    /**
     * ユーザーの生年月日。フォーマットは「yyyy-MM-dd」です。
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * ユーザーの国籍。
     */
    private String nationality;

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
     * 卒業日。フォーマットは「yyyy-MM-dd」です。
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
     * ユーザーの削除フラグ。
     */
    private Boolean deleteFlag;

    /**
     * UsersエンティティからUserInfoDtoへ変換するメソッド。
     *
     * @param user 変換するUsersエンティティ
     * @return Usersエンティティに対応するUserInfoDto
     */
    public static UserInfoDto fromEntity(Users user) {
        return UserInfoDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userNameRomaji(user.getUserNameRomaji())
                .email(user.getEmail())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .nationality(user.getNationality())
                .currentAddress(user.getCurrentAddress())
                .permanentAddress(user.getPermanentAddress())
                .spouse(user.getSpouse())
                .lastSchool(user.getLastSchool())
                .lastSchoolType(user.getLastSchoolType())
                .major(user.getMajor())
                .graduationDate(user.getGraduationDate())
                .educationCourse(user.getEducationCourse())
                .nearestStation(user.getNearestStation())
                .selfPr(user.getSelfPr())
                .deleteFlag(user.getDeleteFlag())
                .build();
    }

    /**
     * UserInfoDtoをUsersエンティティに変換するメソッド。
     *
     * @return Usersエンティティに変換されたインスタンス
     */
    public Users toEntity() {
        return Users.builder()
                .userId(this.userId)
                .userName(this.userName)
                .userNameRomaji(this.userNameRomaji)
                .email(this.email)
                .gender(this.gender)
                .birthDate(this.birthDate)
                .nationality(this.nationality)
                .currentAddress(this.currentAddress)
                .permanentAddress(this.permanentAddress)
                .spouse(this.spouse)
                .lastSchool(this.lastSchool)
                .lastSchoolType(this.lastSchoolType)
                .major(this.major)
                .graduationDate(this.graduationDate)
                .educationCourse(this.educationCourse)
                .nearestStation(this.nearestStation)
                .selfPr(this.selfPr)
                .deleteFlag(this.deleteFlag)
                .build();
    }

    /**
     * UserInfoDtoの内容で指定されたUsersエンティティを更新するメソッド。
     *
     * @param user 更新対象のUsersエンティティ
     */
    public void updateEntity(Users user) {
        user.setUserName(this.userName);
        user.setUserNameRomaji(this.userNameRomaji);
        user.setEmail(this.email);
        user.setGender(this.gender);
        user.setBirthDate(this.birthDate);
        user.setNationality(this.nationality);
        user.setCurrentAddress(this.currentAddress);
        user.setPermanentAddress(this.permanentAddress);
        user.setSpouse(this.spouse);
        user.setLastSchool(this.lastSchool);
        user.setLastSchoolType(this.lastSchoolType);
        user.setMajor(this.major);
        user.setGraduationDate(this.graduationDate);
        user.setEducationCourse(this.educationCourse);
        user.setNearestStation(this.nearestStation);
        user.setSelfPr(this.selfPr);
    }
}
