package com.stylesystem.service;

import org.springframework.stereotype.Service;

import com.stylesystem.model.SkillMaster;
import com.stylesystem.repository.SkillMasterRepository;

import java.util.List;

@Service
public class SkillMasterService {

    private final SkillMasterRepository skillMasterRepository;

    public SkillMasterService(SkillMasterRepository skillMasterRepository) {
        this.skillMasterRepository = skillMasterRepository;
    }

    public List<SkillMaster> getSkillsByCategory(String category) {
        return skillMasterRepository.findByCategory(category);
    }

    public List<SkillMaster> getAllSkills() {
        return skillMasterRepository.findAll();
    }

    public void saveSkill(SkillMaster skillMaster) {
        skillMasterRepository.save(skillMaster);
    }

    public void deleteSkill(Long id) {
        skillMasterRepository.deleteById(id);
    }
}