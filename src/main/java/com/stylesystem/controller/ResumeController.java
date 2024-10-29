package com.stylesystem.controller;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stylesystem.dto.ProjectDto;
import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.UserInfoDto;
import com.stylesystem.model.Project;
import com.stylesystem.repository.SkillMasterRepository;
import com.stylesystem.service.ResumeService;
import com.stylesystem.service.UserDeleteService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final UserDeleteService userDeleteService;

    private final SkillMasterRepository skillMasterRepository;

    @GetMapping("/list")
    public String resumeList(Model model) {
        List<UserInfoDto> userInfoList = userDeleteService.getAllActiveUsers().stream()
                .map(UserInfoDto::fromEntity)
                .collect(Collectors.toList());
        model.addAttribute("users", userInfoList);
        return "resumeList";
    }

    @GetMapping("/view")
    public String resumeView(@RequestParam("userId") String userId, HttpSession session, Model model) {
        
        ResumeDto resumeDto = resumeService.getResumeByUserId(userId);

        if (resumeDto == null) {
            resumeDto = ResumeDto.builder()
                    .userInfo(UserInfoDto.builder().userId(userId).build())
                    .projects(new ArrayList<>())
                    .skillMasters(new ArrayList<>())
                    .build();
        } else {
            log.debug("Projects in ResumeDto: {}", resumeDto.getProjects());
        }

        // save the resumeDto in the session
        session.setAttribute("resumeDto", resumeDto);
        model.addAttribute("resumeDto", resumeDto);
        return "resumeView";
    }

    @GetMapping("/form")
    public String showResumeForm(@RequestParam("userId") String userId, HttpSession session, Model model) {
        // get the resumeDto from the session
        // ResumeDto resumeDto = (ResumeDto) session.getAttribute("resumeDto");
        ResumeDto resumeDto = resumeService.getResumeByUserId(userId);

        // resumeDto is null if the user is creating a new resume
        if (resumeDto == null || resumeDto.getProjects() == null) {
            resumeDto = ResumeDto.builder()
                    .userInfo(UserInfoDto.builder().userId(userId).build())
                    .projects(new ArrayList<>())
                    .build();
            session.setAttribute("resumeDto", resumeDto);
        } else {
            log.debug("Projects in ResumeDto: {}", resumeDto.getProjects());
        }

        // add skills by category to the model
        // Map<String, List<SkillMaster>> skillsByCategory = resumeService.getSkillsByCategory();
        //  model.addAttribute("skillsByCategory", skillsByCategory);
        model.addAttribute("modelDB", skillMasterRepository.findByCategory("db"));
        model.addAttribute("modelOS", skillMasterRepository.findByCategory("os"));
        model.addAttribute("modelHW", skillMasterRepository.findByCategory("hw"));
        model.addAttribute("modelTool", skillMasterRepository.findByCategory("tool"));
        model.addAttribute("modelLanguage", skillMasterRepository.findByCategory("language"));
        model.addAttribute("resumeDto", resumeDto);
        return "resumeForm";
    }

    @PostMapping("/save")
    public String saveResume(@ModelAttribute ResumeDto resumeDto) {
        log.debug("Received ResumeDto: {}", resumeDto.getProjects());
        resumeService.saveResume(resumeDto);
        // log.debug("Resume saved for user: {}", resumeDto.getUserInfo().getUserId());
        return "redirect:/resume/view?userId=" + resumeDto.getUserInfo().getUserId();
    }

    @GetMapping("/add")
    public String AddProject(@ModelAttribute ResumeDto resumeDto) {

       // 新しいProjectDtoを作成
    ProjectDto createproject = new ProjectDto(); // 初期化する

    // ユニークなプロジェクトIDを生成
    UUID uniqueKey = UUID.randomUUID();
    String createProjectId = uniqueKey.toString();

    // プロジェクトIDを設定
    createproject.setProjectId(createProjectId);

    // 新しいプロジェクトを履歴のプロジェクトリストに追加
    List<ProjectDto> projects = resumeDto.getProjects();
    if (projects == null) {
        projects = new ArrayList<>(); // nullの場合、リストを初期化
    }
    projects.add(createproject); // 新しいプロジェクトを追加
    resumeDto.setProjects(projects); // resumeDtoを更新
    
        return "redirect:/resume/form?userId=" + resumeDto.getUserInfo().getUserId();
    }
    
}