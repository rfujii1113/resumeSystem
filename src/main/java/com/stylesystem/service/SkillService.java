package com.stylesystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.stylesystem.dto.SkillDto;
import com.stylesystem.model.SkillMaster;
import com.stylesystem.repository.SkillRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Transactional(readOnly = true)
    public List<SkillMaster> getSkillsByCategory(String category) {
        List<SkillMaster> skills = skillRepository.findByCategory(category);
        return skills;
    } 

    @Transactional(readOnly = true)
    public List<SkillMaster> getAllSkills() {
        List<SkillMaster> skills = skillRepository.findAll();
        return skills;
    }

    @Transactional(readOnly = true)
    public Map<String, List<SkillMaster>> getSkillsByCategory() {
        List<SkillMaster> allSkills = getAllSkills();
        return allSkills.stream().collect(Collectors.groupingBy(SkillMaster::getCategory));
    }
    
    @Transactional
    public void addSkill(SkillDto skillDto) {
        SkillMaster skill = SkillMaster.builder()
                .name(skillDto.getName())
                .category(skillDto.getCategory())
                .build();
        skillRepository.save(skill);
    }

    @Transactional
    public void deleteSkill(@PathVariable("id") Long id) {
        // if (!skillRepository.existsById(id)) {
        //     throw new IllegalArgumentException("Skill not found with id: " + id);
        // }
        skillRepository.deleteById(id);
    }
}