package com.stylesystem.dto;

import lombok.Data;

@Data
public class UserAuthDto {
    private String userId;
    private String password;
    private String role;
}
