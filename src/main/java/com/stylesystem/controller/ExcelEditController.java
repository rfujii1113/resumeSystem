package com.stylesystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.stylesystem.dto.ResumeEditDto;
import com.stylesystem.model.Project;
import com.stylesystem.model.Users;
import com.stylesystem.service.ExcelEditService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * ExcelEditControllerは、Excelファイルの編集とエクスポートに関連する処理を管理するコントローラクラスです。
 * ユーザー情報およびプロジェクト情報をExcelファイルに書き込み、編集結果を返します。
 */
@Controller
@RequiredArgsConstructor 
public class ExcelEditController {

    private final ExcelEditService excelEditService;

    Users users = new Users();
    List<Project> projects = new ArrayList<>();

    /**
     * Excel編集ページを表示するメソッド。
     * 
     * @return "editExcel" テンプレートページの名前
     */
    @GetMapping("editExcel")
    public String showManageExcelPage() {
        return "editExcel";
    }

    /**
     * Excelファイルの編集を開始するメソッド。
     * 
     * @param resumeEditDto 編集対象の履歴書データを含むDTO
     * @param response HTTPレスポンスオブジェクト
     * @return 編集後の履歴書ビューへのリダイレクト
     * @throws IOException 入出力エラーが発生した場合
     */
    @PostMapping("/startManage")
    public String excelEdit(@ModelAttribute ResumeEditDto resumeEditDto, HttpServletResponse response)
            throws IOException {
        excelEditService.InsertExcel(resumeEditDto, response);
        return "redirect:/resume/view";
    }
}
