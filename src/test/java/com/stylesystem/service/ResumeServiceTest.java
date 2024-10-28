// package com.stylesystem.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import com.stylesystem.dto.ResumeDto;
// import com.stylesystem.model.Users;
// import com.stylesystem.repository.ProjectRepository;
// import com.stylesystem.repository.UserRepository;

// @SpringBootTest
// public class ResumeServiceTest {

//     @Autowired
//     private ResumeService resumeService;

//     @MockBean
//     private UserRepository userRepository;

//     @MockBean
//     private ProjectRepository projectRepository;

//     @Test
//     public void testGetResumeByUserId_ReturnsResume_WhenUserExists() {
//         String userId = "20241000";
//         Users user = new Users();
//         user.setUserId(userId);

//         when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
//         ResumeDto resumeDto = resumeService.getResumeByUserId(userId);

//         assertNotNull(resumeDto);
//         assertEquals(userId, resumeDto.getUserInfo().getUserId());
//     }

//     @Test
//     public void testGetResumeByUserId_ReturnsNull_WhenUserNotFound() {
//         String userId = "unknownUser";
//         when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

//         ResumeDto resumeDto = resumeService.getResumeByUserId(userId);
//         assertNull(resumeDto);
//     }
// }
