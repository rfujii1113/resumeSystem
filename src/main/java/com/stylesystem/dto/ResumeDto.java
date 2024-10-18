package com.stylesystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.ArrayList; 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {

    private UserInfoDto userInfo;
    
    @Builder.Default
    private List<ProjectDto> projects = new ArrayList<>(); 
}
