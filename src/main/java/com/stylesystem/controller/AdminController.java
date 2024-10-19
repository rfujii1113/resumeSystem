// src/main/java/com/example/controller/AdminController.java

package com.stylesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.stylesystem.model.SkillMaster;
import com.stylesystem.service.SkillMasterService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SkillMasterService skillMasterService;

    public AdminController(SkillMasterService skillMasterService) {
        this.skillMasterService = skillMasterService;
    }

    @GetMapping("/skills")
    public String manageSkills(Model model) {
        model.addAttribute("skills", skillMasterService.getAllSkills());
        model.addAttribute("newSkill", new SkillMaster());
        return "admin/skills";
    }

    @PostMapping("/skills")
    public String addSkill(@ModelAttribute SkillMaster skillMaster) {
        skillMasterService.saveSkill(skillMaster);
        return "redirect:/admin/skills";
    }

    @GetMapping("/skills/delete/{id}")
    public String deleteSkill(@PathVariable Long id) {
        skillMasterService.deleteSkill(id);
        return "redirect:/admin/skills";
    }
}
