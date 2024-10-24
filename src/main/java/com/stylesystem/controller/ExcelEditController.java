package com.stylesystem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.stylesystem.dto.ResumeEditDTO;
import com.stylesystem.model.Project;
import com.stylesystem.model.Users;
import com.stylesystem.repository.ProjectRepository;
import com.stylesystem.repository.UsersRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor //finalで宣言したもののインスタンス自動生成
public class ExcelEditController {

	private final UsersRepository usersRepository;
	private final ProjectRepository projectRepository;

	Users users = new Users();
	List<Project> projects = new ArrayList<>();

	@GetMapping("editExcel") //クライアントがlocalhost/loginにアクセスしたときに呼ばれる
	public String showManageExcelPage() {
		return "editExcel"; // manageExcel.htmlを返す
	}

	@PostMapping("/startManage") //ExcelDLボタンを押したときに呼ばれる
	public String excelEdit(@ModelAttribute ResumeEditDTO resumeEditDTO, HttpServletResponse response)
			throws IOException {
		users = usersRepository.findByUserId(resumeEditDTO.getUserId());
		projects = projectRepository.findByUsers_UserIdOrderByStartDateAsc(resumeEditDTO.getUserId());

		// Excelテンプレートファイルのパスを指定
		ClassPathResource resource = new ClassPathResource("static/excel/skillsheetTemplate.xlsx");
		// テンプレートExcelファイルを読み込む
		try (InputStream inputStream = resource.getInputStream();
				Workbook workbook = new XSSFWorkbook(inputStream)) {

			// シートを取得
			Sheet sheet = workbook.getSheetAt(0); // 1つ目のシートを取得

			/*
			 * row(横)
			 * cell(縦)
			 */
			//個人情報入力名前
			Row row = sheet.getRow(0); //1行目
			Cell cell = row.getCell(3); // D列
			cell.setCellValue(users.getUserName());

			//性別
			cell = row.getCell(6);
			if(users.getGender()==true){
				cell.setCellValue("女");	
			}else{
				cell.setCellValue("男");
			}

			//生年月日
			cell = row.getCell(8);
			cell.setCellValue(users.getBirthDate());

			//現住所
			row = sheet.getRow(1);
			cell = row.getCell(3);
			cell.setCellValue(users.getCurrentAddress());

			//本籍
			cell = row.getCell(8);
			cell.setCellValue(users.getPermanentAddress());

			//配偶者
			cell = row.getCell(11);
			if(users.getSpouse()==true){
				cell.setCellValue("有");	
			}else{
				cell.setCellValue("無");
			}

			//最終学校
			row = sheet.getRow(2);
			cell = row.getCell(3);
			cell.setCellValue(users.getLastSchool());

			//専攻学科
			cell = row.getCell(8);
			cell.setCellValue(users.getLastSchoolType());

			//卒業年月
			cell = row.getCell(11);
			cell.setCellValue(users.getGraduationDate());

			//教育・研修
			row = sheet.getRow(3);
			cell = row.getCell(3);
			cell.setCellValue(users.getEducationCourse());

			//最寄り駅
			cell = row.getCell(11);
			cell.setCellValue(users.getNearestStation());

			//自己PR
			row = sheet.getRow(49);
			cell = row.getCell(4);
			cell.setCellValue(users.getSelfPr());

			//習得したOS
			row = sheet.getRow(6);
			cell = row.getCell(3);
			cell.setCellValue(String.join(",", projectRepository.findDistinctOSByUserId(resumeEditDTO.getUserId())));

			//習得したDB
			row = sheet.getRow(7);
			cell = row.getCell(3);
			cell.setCellValue(String.join(",", projectRepository.findDistinctDBByUserId(resumeEditDTO.getUserId())));

			//習得した言語
			row = sheet.getRow(8);
			cell = row.getCell(3);
			cell.setCellValue(
					String.join(",", projectRepository.findDistinctLanguagesByUserId(resumeEditDTO.getUserId())));

			//習得したTool
			row = sheet.getRow(9);
			cell = row.getCell(3);
			cell.setCellValue(String.join(",", projectRepository.findDistinctToolByUserId(resumeEditDTO.getUserId())));

			//業務経歴入力
			//シート枚数調整
			if (projects.size() <= 34 && projects.size() > 17) {
				//業務経歴2シート分の時
				workbook.removeSheetAt(2);
			} else if (projects.size() <= 17) {
				//業務経歴1シート分の時
				workbook.removeSheetAt(2);
				workbook.removeSheetAt(1);
			}
			int x = 0;
			int listIndex = 0;
			long sum = 0;
			long monthsBetween = 0;
			for (Project project : projects) {
				//2シート目に移動するときの分岐(17件目)
				if (listIndex == 17) {
					sheet = workbook.getSheetAt(1); // 2つ目のシートを取得
					x = 0;
				}
				//3シート目に移動するときの分岐
				if (listIndex == 34) {
					sheet = workbook.getSheetAt(2); // 3つ目のシートを取得
					x = 0;
				}
				//17件目までの分岐(1シート目)
				if (listIndex < 17) {
					//プロジェクト名
					row = sheet.getRow(15 + x);
					cell = row.getCell(5);
					cell.setCellValue(project.getProjectName());
					//プロジェクト開始日
					row = sheet.getRow(15 + x);
					cell = row.getCell(1);
					cell.setCellValue(project.getStartDate());
					//プロジェクト終了日
					row = sheet.getRow(16 + x);
					cell = row.getCell(1);
					cell.setCellValue(project.getEndDate());
					//開発期間（mヶ月）
					row = sheet.getRow(15 + x);
					cell = row.getCell(4);
					monthsBetween = ChronoUnit.MONTHS.between(project.getStartDate(), project.getEndDate());
					if(monthsBetween == 0) {
						monthsBetween = 1;
					}
					cell.setCellValue(monthsBetween + "ヶ月");
					sum =  sum + monthsBetween;
					//作業場所
					row = sheet.getRow(16 + x);
					cell = row.getCell(5);
					cell.setCellValue(project.getLocation());
					//HW/OS
					row = sheet.getRow(15 + x);
					cell = row.getCell(7);
					//StrringJoinのnullpointErrorを避けるためif分岐
					if (project.getHw() == null || project.getOs() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getHw()) + ","
								+ String.join(",", project.getOs()));
					}
					//DB
					row = sheet.getRow(16 + x);
					cell = row.getCell(7);
					if (project.getDb() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getDb()));
					}
					//language
					row = sheet.getRow(15 + x);
					cell = row.getCell(8);
					if (project.getLanguage() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getLanguage()));
					}
					//Tool
					row = sheet.getRow(16 + x);
					cell = row.getCell(8);
					if (project.getTool() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getTool()));
					}
					//Type
					row = sheet.getRow(15 + x);
					cell = row.getCell(10);
					if (project.getProcesses() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getProcesses()) + ",");
					}
					//row,listindexインクリメント
					x += 2;
					listIndex++;
					//17件目ここでループ
					if (listIndex == 17) {
						continue;
					}
				}

				//2シート目以降(18件目から)
				if (listIndex >= 17) {
					if (listIndex == 17) {
						sheet = workbook.getSheetAt(1); // 2つ目のシートを取得
						x = 0;
					}

					//プロジェクト名
					row = sheet.getRow(3 + x);
					cell = row.getCell(5);
					cell.setCellValue(project.getProjectName());
					//プロジェクト開始日
					row = sheet.getRow(3 + x);
					cell = row.getCell(1);
					cell.setCellValue(project.getStartDate());
					//プロジェクト終了日
					row = sheet.getRow(4 + x);
					cell = row.getCell(1);
					cell.setCellValue(project.getEndDate());
					//開発期間（mヶ月）
					row = sheet.getRow(3 + x);
					cell = row.getCell(4);
					monthsBetween = ChronoUnit.MONTHS.between(project.getStartDate(), project.getEndDate());
					if(monthsBetween == 0) {
						monthsBetween = 1;
					}
					cell.setCellValue(monthsBetween + "ヶ月");
					sum =  sum + monthsBetween;
					//作業場所
					//作業場所
					row = sheet.getRow(4 + x);
					cell = row.getCell(5);
					cell.setCellValue(project.getLocation());
					//HW/OS
					row = sheet.getRow(3 + x);
					cell = row.getCell(7);
					if (project.getHw() == null || project.getOs() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getHw()) + ","
								+ String.join(",", project.getOs()));
					}
					//DB
					row = sheet.getRow(4 + x);
					cell = row.getCell(7);
					if (project.getDb() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getDb()));
					}
					//language
					row = sheet.getRow(3 + x);
					cell = row.getCell(8);
					if (project.getLanguage() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getLanguage()));
					}
					//Tool
					row = sheet.getRow(4 + x);
					cell = row.getCell(8);
					if (project.getTool() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getTool()));
					}
					//Type
					row = sheet.getRow(3 + x);
					cell = row.getCell(10);
					if (project.getProcesses() == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(String.join(",", project.getProcesses()));
					}
					//row,listindexインクリメント
					x += 2;
					listIndex++;
				}
			}
			System.out.println(sum);
			sheet = workbook.getSheetAt(0); // 1つ目のシートを取得
			row = sheet.getRow(3);
			cell = row.getCell(8);
			if(sum/12>=1) {
			cell.setCellValue(sum / 12 + "年" + sum % 12 + "ヶ月" );
			}else {
				cell.setCellValue(sum + "ヶ月" );
			}
			// Excelファイルのダウンロードのためのレスポンスの設定
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\"outputSkillSheet.xlsx\"");

			// 出力用のストリームを用意
			try (ServletOutputStream out = response.getOutputStream()) {
				workbook.write(out); // 変更を保存
			}
		}
		return "redirect:/resume/view";
	}
}