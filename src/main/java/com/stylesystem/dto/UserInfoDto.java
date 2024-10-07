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
    private boolean gender;
    private LocalDate birthDate;
    private String currentAddress;
    private String permanentAddress;
    private boolean spouse;
    private String lastSchool;
    private String lastSchoolType;
    private String major;
    private LocalDate graduationDate;
    private String educationCourse;
    private String nearestStation;
    private String role;
    private boolean deleteFlag;
    private String selfPr;

    // convert entity to dto 
    public static UserInfoDto fromEntity(Users user) {
        return UserInfoDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userNameRomaji(user.getUserNameRomaji())
                .email(user.getEmail())
                .gender(user.isGender())
                .birthDate(user.getBirthDate())
                .currentAddress(user.getCurrentAddress())
                .permanentAddress(user.getPermanentAddress())
                .spouse(user.isSpouse())
                .lastSchool(user.getLastSchool())
                .lastSchoolType(user.getLastSchoolType())
                .major(user.getMajor())
                .graduationDate(user.getGraduationDate())
                .educationCourse(user.getEducationCourse())
                .nearestStation(user.getNearestStation())
                .role(user.getRole())
                .deleteFlag(user.isDeleteFlag())
                .selfPr(user.getSelfPr())
                .build();
    }
}
