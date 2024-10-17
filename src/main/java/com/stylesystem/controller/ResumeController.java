package com.stylesystem.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.UserInfoDto;
import com.stylesystem.service.ResumeService;
import com.stylesystem.service.UserDeleteService;

import lombok.RequiredArgsConstructor;

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
    public String resumeView(Model model) {
        ResumeDto resumeDto = resumeService.getResumeForCurrentUser();
        model.addAttribute("resumeDto", resumeDto);
        return "resumeView";
    }

    @GetMapping("/form")
    public String showResumeForm(Model model) {
        ResumeDto resumeDto = resumeService.getResumeForCurrentUser();

        // If the user does not have a resume, create a new one
        if (resumeDto == null) {
            resumeDto = ResumeDto.builder()
                    .userInfo(new UserInfoDto())
                    .projects(new ArrayList<>())
                    .build();
        } else {
            // Ensure userInfo is not null
            if (resumeDto.getUserInfo() == null) {
                resumeDto.setUserInfo(new UserInfoDto());
            }
            // Ensure projects list is not null
            if (resumeDto.getProjects() == null) {
                resumeDto.setProjects(new ArrayList<>());
            }
        }
        model.addAttribute("resumeDto", resumeDto);
        return "resumeForm";
    }

    @PostMapping("/save")
    public String saveResume(@ModelAttribute ResumeDto resumeDto) {
        resumeService.saveResume(resumeDto);
        return "redirect:/resume/view";
    }
}
