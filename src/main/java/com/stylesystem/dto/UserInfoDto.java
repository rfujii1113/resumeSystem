package com.stylesystem.dto;

import com.stylesystem.model.Users;

import java.time.LocalDate;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {

    private String userId;
    private String userName;
    private String userNameRomaji;
    private String email;
    private Boolean gender;
    private LocalDate birthDate;
    private String currentAddress;
    private String permanentAddress;
    private Boolean spouse;
    private String lastSchool;
    private String lastSchoolType;
    private String major;
    private LocalDate graduationDate;
    private String educationCourse;
    private String nearestStation;
    private String role;
    private Boolean deleteFlag;
    private String selfPr;

    // convert entity to dto 
    public static UserInfoDto fromEntity(Users user) {
        return UserInfoDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userNameRomaji(user.getUserNameRomaji())
                .email(user.getEmail())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .currentAddress(user.getCurrentAddress())
                .permanentAddress(user.getPermanentAddress())
                .spouse(user.getSpouse())
                .lastSchool(user.getLastSchool())
                .lastSchoolType(user.getLastSchoolType())
                .major(user.getMajor())
                .graduationDate(user.getGraduationDate())
                .educationCourse(user.getEducationCourse())
                .nearestStation(user.getNearestStation())
                .role(user.getRole())
                .deleteFlag(user.getDeleteFlag())
                .selfPr(user.getSelfPr())
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .userId(this.userId)
                .userName(this.userName)
                .userNameRomaji(this.userNameRomaji)
                .email(this.email)
                .gender(this.gender)
                .birthDate(this.birthDate)
                .currentAddress(this.currentAddress)
                .permanentAddress(this.permanentAddress)
                .spouse(this.spouse)
                .lastSchool(this.lastSchool)
                .lastSchoolType(this.lastSchoolType)
                .major(this.major)
                .graduationDate(this.graduationDate)
                .educationCourse(this.educationCourse)
                .nearestStation(this.nearestStation)
                .role(this.role)
                .deleteFlag(this.deleteFlag)
                .selfPr(this.selfPr)
                .build();
    }
}
