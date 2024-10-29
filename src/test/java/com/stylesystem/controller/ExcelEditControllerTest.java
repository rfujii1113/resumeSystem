package com.stylesystem.controller;

import com.stylesystem.dto.ResumeEditDTO;
import com.stylesystem.model.Project;
import com.stylesystem.model.Users;
import com.stylesystem.repository.ProjectRepository;
import com.stylesystem.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ExcelEditControllerTest {

    @InjectMocks
    private ExcelEditController excelEditController;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ProjectRepository projectRepository;

    private Users mockUser;
    private List<Project> mockProjects;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // モックユーザーの設定
        mockUser = new Users();
        mockUser.setUserId("00000001");
        mockUser.setUserName("山田太郎");
        mockUser.setGender(true);
        mockUser.setSpouse(false);
        mockUser.setBirthDate(LocalDate.of(2001, 11, 1));
        mockUser.setCurrentAddress("愛知");
        mockUser.setPermanentAddress("広島");
        mockUser.setLastSchool("テスト大学");
        mockUser.setMajor("コンピュータ科学");
        mockUser.setGraduationDate(LocalDate.of(2024, 3, 30));
        mockUser.setEducationCourse("情報技術");
        mockUser.setNearestStation("名古屋駅");
        mockUser.setSelfPr("熱意を持っています");

        // モックプロジェクトの設定
        Project project = new Project();
        project.setProjectName("プロジェクトA");
        project.setStartDate(LocalDate.of(2024, 10, 1));
        project.setEndDate(LocalDate.of(2024, 10, 31));
        project.setLocation("名古屋");
        mockProjects = Collections.singletonList(project);
    }

    @Test
    void testShowManageExcelPage() {
        // Given: Excel管理ページを表示するためのテスト
        String result = excelEditController.showManageExcelPage();
        
        // Then: 結果が期待するページにリダイレクトされることを確認
        assertEquals("editExcel", result);
    }

    @Test
    void testExcelEdit() throws Exception {
        // Given: ResumeEditDTOオブジェクトを作成
        ResumeEditDTO resumeEditDTO = new ResumeEditDTO();
        resumeEditDTO.setUserId("00000001");

        // When: リポジトリからモックされたユーザーとプロジェクトを取得する設定
        when(usersRepository.findByUserId("00000001")).thenReturn(mockUser);
        when(projectRepository.findByUsers_UserIdOrderByStartDateAsc("00000001")).thenReturn(mockProjects);

        // When: excelEditメソッドを呼び出す
        MockHttpServletResponse response = new MockHttpServletResponse();
        String result = excelEditController.excelEdit(resumeEditDTO, response);

        // Then: Excelファイルが正常に処理されるかの確認
        assertEquals("redirect:/resume/view", result);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
