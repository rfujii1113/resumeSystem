package com.stylesystem.repository;

import com.stylesystem.model.SkillMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * スキル情報を管理するリポジトリインターフェース。
 * カテゴリに基づいてスキルを検索する機能を提供します。
 */
public interface SkillRepository extends JpaRepository<SkillMaster, Long> {

    /**
     * 指定されたカテゴリに一致するスキルのリストを取得します。
     *
     * @param category 検索対象のスキルカテゴリ
     * @return 指定されたカテゴリに一致するスキルのリスト
     */
    List<SkillMaster> findByCategory(String category);
}
