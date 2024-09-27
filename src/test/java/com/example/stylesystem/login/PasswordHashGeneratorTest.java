package com.example.stylesystem.login;

import org.junit.jupiter.api.Test;

import com.stylesystem.util.PasswordHashGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordHashGeneratorTest {

    @Test
    public void testHashPassword() {
        String rawPassword = "myPassword";
        String hashedPassword = PasswordHashGenerator.hashPassword(rawPassword);

        assertNotNull(hashedPassword); 
        assertNotEquals(rawPassword, hashedPassword);  
    }

    @Test
    public void testMatchPassword() {
        String rawPassword = "myPassword";
        String hashedPassword = PasswordHashGenerator.hashPassword(rawPassword);

        assertTrue(PasswordHashGenerator.matchPassword(rawPassword, hashedPassword));  
        assertFalse(PasswordHashGenerator.matchPassword("wrongPassword", hashedPassword)); 
    }
}
