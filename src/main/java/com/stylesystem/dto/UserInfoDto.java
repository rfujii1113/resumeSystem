package com.stylesystem.dto;

import com.stylesystem.model.Users;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // フォーマットを指定
    private LocalDate birthDate;
    private String nationality;
    private String currentAddress;
    private String permanentAddress;
    private Boolean spouse;
    private String lastSchool;
    private String lastSchoolType;
    private String major;
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // フォーマットを指定
    private LocalDate graduationDate;
    private String educationCourse;
    private String nearestStation;
    private String selfPr;
    private Boolean deleteFlag;

    // convert entity to dto 
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

    // convert dto to entity
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

    // update entity with dto
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
