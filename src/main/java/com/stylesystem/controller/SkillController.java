package com.stylesystem.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stylesystem.dto.SkillDto;
import com.stylesystem.service.SkillService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController {

        private final SkillService skillService;
        
        @GetMapping("/main")
        public String skill(Model model) {
                // Get the skills by category
                Map<String, List<SkillDto>> skillsByCategory = skillService.getSkillsByCategory().entrySet().stream()
                                .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                entry -> entry.getValue().stream()
                                                                .map(skill -> SkillDto.builder()
                                                                                .id(skill.getId())
                                                                                .name(skill.getName())
                                                                                .category(skill.getCategory())
                                                                                .build())
                                                                .collect(Collectors.toList())));

                model.addAttribute("skillsByCategory", skillsByCategory);
                model.addAttribute("skillDto", new SkillDto());
                return "skillMain";
        }

        @PostMapping("/add")
        public String addSkill(@ModelAttribute SkillDto skillDto) {
                skillService.addSkill(skillDto); 
                return "redirect:/skill/main";
        }

        @PostMapping("/delete/{id}")
        public String deleteSkill(@PathVariable("id") Long id) {
                skillService.deleteSkill(id);
                return "redirect:/skill/main";
        }
}
