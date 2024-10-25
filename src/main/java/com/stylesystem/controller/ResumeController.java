package com.stylesystem.controller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.UserInfoDto;
import com.stylesystem.model.SkillMaster;
import com.stylesystem.service.ResumeService;
import com.stylesystem.service.UserDeleteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final UserDeleteService userDeleteService;

    @GetMapping("/list")
    public String resumeList(Model model) {
        List<UserInfoDto> userInfoList = userDeleteService.getAllActiveUsers().stream()
                .map(UserInfoDto::fromEntity)
                .collect(Collectors.toList());
        model.addAttribute("users", userInfoList);
        return "resumeList";
    }

    @GetMapping("/view")
    public String resumeView(@RequestParam("userId") String userId, Model model) {
        ResumeDto resumeDto = resumeService.getResumeByUserId(userId);

        if (resumeDto == null) {
            resumeDto = ResumeDto.builder()
                    .userInfo(UserInfoDto.builder().userId(userId).build()) 
                    .projects(new ArrayList<>()) 
                    .SkillMasters(new ArrayList<>()) 
                    .build();
        }else{
            log.debug("Projects in ResumeDto: {}", resumeDto.getProjects());
        }
        model.addAttribute("resumeDto", resumeDto);
        return "resumeView";
    }

    @GetMapping("/form")
    public String showResumeForm(@RequestParam("userId") String userId, Model model) {
        ResumeDto resumeDto = resumeService.getResumeByUserId(userId);

        // If the resume is not found, create a new one
        if (resumeDto == null || resumeDto.getProjects() == null) {
            resumeDto = ResumeDto.builder()
                    .userInfo(UserInfoDto.builder().userId(userId).build())
                    .projects(new ArrayList<>())
                    .SkillMasters(new ArrayList<>())
                    .build();

            // log.debug("Projects in ResumeDto: {}", resumeDto.getProjects());
        } else {
            log.debug("Projects in ResumeDto: {}", resumeDto.getProjects());
        }

        // Get the skills grouped by category
        Map<String, List<SkillMaster>> skillsByCategory = resumeService.getSkillsByCategory();
        model.addAttribute("skillsByCategory", skillsByCategory);
        model.addAttribute("resumeDto", resumeDto);
        return "resumeForm";
    }

    @PostMapping("/save")
    public String saveResume(@ModelAttribute ResumeDto resumeDto) {
        // log.debug("Received ResumeDto: {}", resumeDto);
        resumeService.saveResume(resumeDto);
        // log.debug("Resume saved for user: {}", resumeDto.getUserInfo().getUserId());
        return "redirect:/resume/view?userId=" + resumeDto.getUserInfo().getUserId();
    }
}
