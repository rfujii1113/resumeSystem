package com.stylesystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthDto {
    private String userId;
    private String password;
    private String confirmPassword; 
    private String role;
}
