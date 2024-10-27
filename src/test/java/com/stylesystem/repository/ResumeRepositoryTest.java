// package com.stylesystem.repository;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

// import com.stylesystem.model.Users;

// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
// public class ResumeRepositoryTest {

//     @Autowired
//     private ResumeRepository resumeRepository;

//     @BeforeEach
//     public void setUp() {
//         Users user = new Users();
//         user.setUserId("20241000");
//         resumeRepository.save(user);
//     }

//     @Test
//     public void testFindByUserId_ReturnsUser_WhenUserExists() {
//         Optional<Users> foundUser = resumeRepository.findByUserId("20241000");
//         assertTrue(foundUser.isPresent());
//         assertEquals("20241000", foundUser.get().getUserId());
//     }

//     @Test
//     public void testFindByUserId_ReturnsEmpty_WhenUserNotFound() {
//         Optional<Users> foundUser = resumeRepository.findByUserId("unknownUser");
//         assertFalse(foundUser.isPresent());
//     }
// }
