package com.stylesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * スキル情報を表すデータ転送オブジェクト（DTO）。
 * スキルID、名前、カテゴリを含みます。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDto {

    /**
     * スキルのユニーク識別子。
     */
    private Long id;

    /**
     * スキルの名前。
     */
    private String name;

    /**
     * スキルのカテゴリ（例：言語、データベース、ツールなど）。
     */
    private String category;
}
