package com.stylesystem.dto;

import java.util.List;

import com.stylesystem.model.Skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDto {
    private List<String> os;
    private List<String> db;
    private List<String> language;
    private List<String> tool;

    public Skill toEntity() {
        return Skill.builder()
                .os(String.join(",", os))
                .db(String.join(",", db))
                .language(String.join(",", language))
                .tool(String.join(",", tool))
                .build();
    }

    // public static SkillDto fromEntity(Skill skill) {
    //     return SkillDto.builder()
    //             .os(List.of(skill.getOs().split(",")))
    //             .db(List.of(skill.getDb().split(",")))
    //             .language(List.of(skill.getLanguage().split(",")))
    //             .tool(List.of(skill.getTool().split(",")))
    //             .build();
    // }

    public static SkillDto fromEntity(Skill skill) {
        return SkillDto.builder()
                .os(skill.getOs() != null && !skill.getOs().isEmpty() ? List.of(skill.getOs().split(",")) : List.of())
                .db(skill.getDb() != null && !skill.getDb().isEmpty() ? List.of(skill.getDb().split(",")) : List.of())
                .language(skill.getLanguage() != null && !skill.getLanguage().isEmpty() ? List.of(skill.getLanguage().split(",")) : List.of())
                .tool(skill.getTool() != null && !skill.getTool().isEmpty() ? List.of(skill.getTool().split(",")) : List.of())
                .build();
    }
}
