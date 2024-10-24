package com.stylesystem.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.stylesystem.model.Users;

@DataJpaTest
public class ResumeRepositoryTest {

    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    public void testFindByUserId_ReturnsUser_WhenUserExists() {
        // given
        Users user = new Users();
        user.setUserId("testUser");
        resumeRepository.save(user);

        // when
        Optional<Users> foundUser = resumeRepository.findByUserId("testUser");

        // then
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUserId());
    }

    @Test
    public void testFindByUserId_ReturnsEmpty_WhenUserNotFound() {
        // when
        Optional<Users> foundUser = resumeRepository.findByUserId("unknownUser");

        // then
        assertFalse(foundUser.isPresent());
    }
}
