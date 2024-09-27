package com.stylesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  
@NoArgsConstructor  
@AllArgsConstructor  
public class LoginRequestDTO {
    private int empId;         
    private String password;  
}
