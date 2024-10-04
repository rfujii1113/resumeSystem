package com.stylesystem.dto;

import lombok.Data;

@Data
public class UserPasswordResetDto {
    private String userId; 
    private String newPassword; 
}
