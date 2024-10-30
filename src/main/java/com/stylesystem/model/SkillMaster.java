package com.stylesystem.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * スキル情報を保持するエンティティクラス。
 * 各スキルの名称とカテゴリを管理します。
 */
@Entity
@Table(name = "skill_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillMaster {

    /**
     * スキルのユニーク識別子。自動生成されます。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * スキルの名前。
     */
    private String name;

    /**
     * スキルのカテゴリ（例：言語、ツールなど）。
     */
    private String category;
}
