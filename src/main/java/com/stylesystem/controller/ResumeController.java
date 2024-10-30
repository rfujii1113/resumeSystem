package com.stylesystem.controller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stylesystem.dto.ResumeDto;
import com.stylesystem.dto.UserInfoDto;
import com.stylesystem.model.SkillMaster;
import com.stylesystem.service.ResumeService;
import com.stylesystem.service.SkillService;
import com.stylesystem.service.UserDeleteService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 経歴書に関連する操作を管理するコントローラークラス。
 * 経歴書のリスト表示、詳細表示、フォーム表示、および保存機能を提供します。
 */
@Slf4j
@Controller
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final SkillService skillService;
    private final UserDeleteService userDeleteService;

    /**
     * アクティブな全ユーザーの経歴書リストを表示するメソッド。
     * 
     * @param model 経歴書リストを格納するためのモデル
     * @return 経歴書リストページのビュー名
     */
    @GetMapping("/list")
    public String resumeList(Model model) {
        List<UserInfoDto> userInfoList = userDeleteService.getAllActiveUsers().stream()
                .map(UserInfoDto::fromEntity)
                .collect(Collectors.toList());
        model.addAttribute("users", userInfoList);
        return "resumeList";
    }

    /**
     * 指定されたユーザーIDの経歴書詳細を表示するメソッド。
     * 経歴書情報をセッションに保存し、モデルに追加します。
     * 
     * @param userId ユーザーのID
     * @param session 経歴書情報を保存するためのセッション
     * @param model 経歴書情報を格納するモデル
     * @return 経歴書詳細ページのビュー名
     */
    @GetMapping("/view")
    public String resumeView(@RequestParam("userId") String userId, HttpSession session, Model model) {
        ResumeDto resumeDto = resumeService.getResumeByUserId(userId);

        if (resumeDto == null) {
            resumeDto = ResumeDto.builder()
                    .userInfo(UserInfoDto.builder().userId(userId).build())
                    .projects(new ArrayList<>())
                    .skillMasters(new ArrayList<>())
                    .build();
        } else {
            log.debug("Projects in ResumeDto: {}", resumeDto.getProjects());
        }

        // 経歴書情報をセッションに保存
        session.setAttribute("resumeDto", resumeDto);
        model.addAttribute("resumeDto", resumeDto);
        return "resumeView";
    }

    /**
     * 経歴書作成フォームを表示するメソッド。
     * 経歴書情報をセッションから取得し、スキルカテゴリ情報をモデルに追加します。
     * 
     * @param userId ユーザーのID
     * @param session 経歴書情報を保存するためのセッション
     * @param model 経歴書情報およびスキルカテゴリ情報を格納するモデル
     * @return 経歴書作成フォームのビュー名
     */
    @GetMapping("/form")
    public String showResumeForm(@RequestParam("userId") String userId, HttpSession session, Model model) {
        // セッションから経歴書情報を取得
        ResumeDto resumeDto = (ResumeDto) session.getAttribute("resumeDto");

        // 新規経歴書作成の場合
        if (resumeDto == null || resumeDto.getProjects() == null) {
            resumeDto = ResumeDto.builder()
                    .userInfo(UserInfoDto.builder().userId(userId).build())
                    .projects(new ArrayList<>())
                    .build();
            session.setAttribute("resumeDto", resumeDto);
        } else {
            log.debug("Projects in ResumeDto: {}", resumeDto.getProjects());
        }

        // スキルカテゴリ情報をモデルに追加
        Map<String, List<SkillMaster>> skillsByCategory = skillService.getSkillsByCategory();
        model.addAttribute("skillsByCategory", skillsByCategory);
        model.addAttribute("resumeDto", resumeDto);
        return "resumeForm";
    }

    /**
     * 経歴書情報を保存するメソッド。
     * 保存が完了したら、経歴書の詳細ページにリダイレクトします。
     * 
     * @param resumeDto 保存する経歴書のDTO
     * @return 経歴書詳細ページへのリダイレクトURL
     */
    @PostMapping("/save")
    public String saveResume(@ModelAttribute ResumeDto resumeDto) {
        resumeService.saveResume(resumeDto);
        return "redirect:/resume/view?userId=" + resumeDto.getUserInfo().getUserId();
    }
}
