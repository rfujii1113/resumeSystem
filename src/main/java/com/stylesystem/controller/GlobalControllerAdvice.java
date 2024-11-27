package com.stylesystem.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("userRole")
    public String getUserRole(HttpServletRequest request) {

        String userRole = (String) request.getSession().getAttribute("userRole");

        if (userRole != null) {
            return userRole.equals("ROLE_ADMIN") ? "ROLE_ADMIN" : "ROLE_USER";
        }

        return "ROLE_USER";
    }
}
