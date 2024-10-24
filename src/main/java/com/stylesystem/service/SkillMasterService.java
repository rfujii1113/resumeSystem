package com.stylesystem.service;

import org.springframework.stereotype.Service;

import com.stylesystem.model.SkillMaster;
import com.stylesystem.repository.SkillMasterRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SkillMasterService {

    private static final Logger logger = LoggerFactory.getLogger(SkillMasterService.class);

    private final SkillMasterRepository skillMasterRepository;

    public SkillMasterService(SkillMasterRepository skillMasterRepository) {
        this.skillMasterRepository = skillMasterRepository;
    }

    public List<SkillMaster> getSkillsByCategory(String category) {
        List<SkillMaster> skills = skillMasterRepository.findByCategory(category);
        logger.debug("Category: {}, Skills: {}", category, skills);
        return skills;
    } 

    public List<SkillMaster> getAllSkills() {
        List<SkillMaster> skills = skillMasterRepository.findAll();
        logger.debug("All Skills: {}", skills);
        return skills;
    }

    public void saveSkill(SkillMaster skillMaster) {
        skillMasterRepository.save(skillMaster);
    }

    public void deleteSkill(Long id) {
        skillMasterRepository.deleteById(id);
    }

    public Map<String, List<SkillMaster>> getSkillsGroupedByCategory(Map<String, String> categoryDisplayNames) {
        Map<String, List<SkillMaster>> skillsByCategory = new LinkedHashMap<>();

        for (String category : categoryDisplayNames.keySet()) {
            List<SkillMaster> skillsInCategory = getSkillsByCategory(category);
            logger.debug("Category: {}, Skills in Category: {}", category, skillsInCategory);
            skillsByCategory.put(category, skillsInCategory);
        }

        return skillsByCategory;
    }
}