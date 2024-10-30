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

/**
 * スキル情報を管理するサービスクラス。
 * スキルのカテゴリ別取得、追加、削除機能を提供します。
 */
@Service
public class SkillService {

    private final SkillRepository skillRepository;

    /**
     * SkillServiceのコンストラクタ。
     *
     * @param skillRepository スキル情報を管理するリポジトリ
     */
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /**
     * 指定されたカテゴリに属するスキルを取得します。
     *
     * @param category 取得するスキルのカテゴリ
     * @return 指定されたカテゴリに属するスキルのリスト
     */
    @Transactional(readOnly = true)
    public List<SkillMaster> getSkillsByCategory(String category) {
        List<SkillMaster> skills = skillRepository.findByCategory(category);
        return skills;
    } 

    /**
     * 全スキルを取得します。
     *
     * @return 全スキルのリスト
     */
    @Transactional(readOnly = true)
    public List<SkillMaster> getAllSkills() {
        List<SkillMaster> skills = skillRepository.findAll();
        return skills;
    }

    /**
     * 全スキルをカテゴリ別にグループ化して取得します。
     *
     * @return カテゴリ別にグループ化されたスキルのマップ
     */
    @Transactional(readOnly = true)
    public Map<String, List<SkillMaster>> getSkillsByCategory() {
        List<SkillMaster> allSkills = getAllSkills();
        return allSkills.stream().collect(Collectors.groupingBy(SkillMaster::getCategory));
    }
    
    /**
     * 新しいスキルを追加します。
     *
     * @param skillDto 追加するスキルの情報を持つDTO
     */
    @Transactional
    public void addSkill(SkillDto skillDto) {
        SkillMaster skill = SkillMaster.builder()
                .name(skillDto.getName())
                .category(skillDto.getCategory())
                .build();
        skillRepository.save(skill);
    }

    /**
     * 指定されたIDのスキルを削除します。
     *
     * @param id 削除するスキルのID
     */
    @Transactional
    public void deleteSkill(@PathVariable("id") Long id) {
        skillRepository.deleteById(id);
    }
}
