package com.stylesystem.controller;

import com.stylesystem.dto.ResumeDto;
import com.stylesystem.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping("/list")
    public String resumeList(Model model) {
        List<ResumeDto> resumeList = resumeService.getAllActiveUsers().stream()
                .map(ResumeDto::fromEntity)
                .collect(Collectors.toList());
        model.addAttribute("users", resumeList);
        return "resumeList";
    }

    @GetMapping("/form")
    public String resumeForm(Model model) {
        model.addAttribute("resume", new ResumeDto());
        return "resumeForm";
    }

    @PostMapping("/save")
    public String saveResume(@ModelAttribute ResumeDto resumeDto) {
        resumeService.saveResume(resumeDto);
        return "redirect:/resume/list";
    }
}
