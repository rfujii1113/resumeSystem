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

/**
 * スキル管理に関する操作を行うコントローラークラス。
 * スキルの追加、削除、カテゴリごとの表示機能を提供します。
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    /**
     * スキルのメインページを表示するメソッド。
     * スキルをカテゴリ別に取得してモデルに追加します。
     *
     * @param model カテゴリ別のスキル情報を格納するモデル
     * @return スキルメインページのビュー名
     */
    @GetMapping("/main")
    public String skill(Model model) {
        // カテゴリ別にスキルを取得
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

    /**
     * 新しいスキルを追加するメソッド。
     * フォームから入力されたスキル情報を保存し、スキルメインページにリダイレクトします。
     *
     * @param skillDto 追加するスキルの情報を含むDTO
     * @return スキルメインページへのリダイレクトURL
     */
    @PostMapping("/add")
    public String addSkill(@ModelAttribute SkillDto skillDto) {
        skillService.addSkill(skillDto); 
        return "redirect:/skill/main";
    }

    /**
     * 指定されたIDのスキルを削除するメソッド。
     * 削除後、スキルメインページにリダイレクトします。
     *
     * @param id 削除するスキルのID
     * @return スキルメインページへのリダイレクトURL
     */
    @PostMapping("/delete/{id}")
    public String deleteSkill(@PathVariable("id") Long id) {
        skillService.deleteSkill(id);
        return "redirect:/skill/main";
    }
}
