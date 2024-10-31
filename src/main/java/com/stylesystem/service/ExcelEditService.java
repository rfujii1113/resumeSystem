package com.stylesystem.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.stylesystem.dto.ResumeEditDto;
import com.stylesystem.model.Project;
import com.stylesystem.model.Users;
import com.stylesystem.repository.ProjectRepository;
import com.stylesystem.repository.UserRepository;

import jakarta.servlet.ServletOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;


/**
 * ExcelEditServiceは、ユーザーの履歴書情報をExcelファイルに書き込むサービスクラスです。
 * ユーザー情報やプロジェクト情報を取得し、Excelテンプレートを編集してレスポンスとして返します。
 */
@Service
@RequiredArgsConstructor // finalで宣言したもののインスタンス自動生成
public class ExcelEditService {

	private final UserRepository userRepository;
	private final ProjectRepository projectRepository;

	Users users = new Users();
	List<Project> projects = new ArrayList<>();


	/**
     * 指定された履歴書データに基づいてExcelファイルを生成し、HTTPレスポンスとして返します。
     *
     * @param resumeEditDto 編集対象の履歴書データを含むDTO
     * @param response HTTPレスポンスオブジェクト
     * @throws IOException 入出力エラーが発生した場合
     */
	public void InsertExcel(ResumeEditDto resumeEditDto, HttpServletResponse response) throws IOException {

		users = userRepository.findByUserId(resumeEditDto.getUserId());
		projects = projectRepository.findByUsers_UserIdOrderByStartDateDesc(resumeEditDto.getUserId());

		// Excelテンプレートファイルのパスを指定
		ClassPathResource resource = new ClassPathResource("static/excel/skillsheetTemplate.xlsx");
		// テンプレートExcelファイルを読み込む
		try (InputStream inputStream = resource.getInputStream();
				Workbook workbook = new XSSFWorkbook(inputStream)) {
			
			// シートを取得
			Sheet sheet = workbook.getSheetAt(0); // 1つ目のシートを取得

			//個人情報入力 名前
			Row nameRow = sheet.getRow(0); //1行目
			Cell nameCell = nameRow.getCell(3); // D列
			nameCell.setCellValue(users.getUserName());

			//性別
			Row genderRow = sheet.getRow(0);
			Cell genderCell = genderRow.getCell(6);
			if(users.getGender()==true){
				genderCell.setCellValue("女");	
			}else{
				genderCell.setCellValue("男");
			}

			//生年月日
			Row birthDateRow = sheet.getRow(0);
			Cell birthDateCell = birthDateRow.getCell(8);
			birthDateCell.setCellValue(users.getBirthDate());

			//現住所
			Row CurrentAddressRow = sheet.getRow(1);
			Cell CurrentAddressCell = CurrentAddressRow.getCell(3);
			CurrentAddressCell.setCellValue(users.getCurrentAddress());

			//本籍
			Row PermanentAddressRow = sheet.getRow(1);
			Cell PermanentAddressCell = PermanentAddressRow.getCell(8);
			PermanentAddressCell.setCellValue(users.getPermanentAddress());

			//配偶者
			Row SpouseRow = sheet.getRow(1);
			Cell SpouseCell = SpouseRow.getCell(11);
			if(users.getSpouse()==true){
				SpouseCell.setCellValue("有");	
			}else{
				SpouseCell.setCellValue("無");
			}

			//最終学校
			Row LastSchoolRow = sheet.getRow(2);
			Cell LastSchoolCell = LastSchoolRow.getCell(3);
			LastSchoolCell.setCellValue(users.getLastSchool());

			//専攻学科
			Row MajorRow = sheet.getRow(2);
			Cell MajorCell = MajorRow.getCell(8);
			MajorCell.setCellValue(users.getMajor());

			//卒業年月
			Row GraduationDateRow = sheet.getRow(2);
			Cell GraduationDateCell = GraduationDateRow.getCell(11);
			GraduationDateCell.setCellValue(users.getGraduationDate());

			//教育・研修
			Row EducationCourseRow = sheet.getRow(3);
			Cell EducationCourseCell = EducationCourseRow.getCell(3);
			EducationCourseCell.setCellValue(users.getEducationCourse());

			//最寄り駅
			Row NearestStationRow = sheet.getRow(3);
			Cell NearestStationCell = NearestStationRow.getCell(11);
			NearestStationCell.setCellValue(users.getNearestStation());

			//自己PR
			Row SelfPrRow = sheet.getRow(49);
			Cell SelfPrCell = SelfPrRow.getCell(4);
			SelfPrCell.setCellValue(users.getSelfPr());

			//習得したOS
			Row GetOSRow = sheet.getRow(6);
			Cell GetOSCell = GetOSRow.getCell(3);
			GetOSCell.setCellValue(String.join(",", projectRepository.findDistinctOSByUserId(resumeEditDto.getUserId())));

			//習得したDB
			Row GetDBRow = sheet.getRow(7);
			Cell GetDBcell = GetDBRow.getCell(3);
			GetDBcell.setCellValue(String.join(",", projectRepository.findDistinctDBByUserId(resumeEditDto.getUserId())));

			//習得した言語
			Row GetLanguageRow = sheet.getRow(8);
			Cell GetLanguageCell = GetLanguageRow.getCell(3);
			GetLanguageCell.setCellValue(
					String.join(",", projectRepository.findDistinctLanguagesByUserId(resumeEditDto.getUserId())));

			//習得したTool
			Row GetToolRow = sheet.getRow(9);
			Cell GetToolCell = GetToolRow.getCell(3);
			GetToolCell.setCellValue(String.join(",", projectRepository.findDistinctToolByUserId(resumeEditDto.getUserId())));

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
			//rowのindex
			int x = 0;
			//projectリストのindex
			int listIndex = 0;
			//経験年数sum
			long sum = 0;
			//project期間計算param
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
					Row ProjectNameRow = sheet.getRow(15 + x);
					Cell ProjectNameCell = ProjectNameRow.getCell(5);
					ProjectNameCell.setCellValue(project.getProjectName());
					//プロジェクト開始日
					Row StarDateRow = sheet.getRow(15 + x);
					Cell StarDateCell = StarDateRow.getCell(1);
					StarDateCell.setCellValue(project.getStartDate());
					//プロジェクト終了日
					Row EndDateRow = sheet.getRow(16 + x);
					Cell EndDateCell = EndDateRow.getCell(1);
					EndDateCell.setCellValue(project.getEndDate());
					//開発期間（mヶ月）
					Row monthsBetweenRow = sheet.getRow(15 + x);
					Cell monthsBetweenCell = monthsBetweenRow.getCell(4);
					monthsBetween = ChronoUnit.MONTHS.between(project.getStartDate(), project.getEndDate());
					if(monthsBetween == 0) {
						monthsBetween = 1;
					}
					monthsBetweenCell.setCellValue(monthsBetween + "ヶ月");
					sum =  sum + monthsBetween;
					//作業場所
					Row LocationRow = sheet.getRow(16 + x);
					Cell LocationCell = LocationRow.getCell(5);
					LocationCell.setCellValue(project.getLocation());
					//HW/OS
					Row HWDBRow = sheet.getRow(15 + x);
					Cell HWDBCell = HWDBRow.getCell(7);
					//StrringJoinのnullpointErrorを避けるためif分岐
					if (project.getHw() == null || project.getOs() == null) {
						HWDBCell.setCellValue("");
					} else {
						HWDBCell.setCellValue(String.join(",", project.getHw()) + ","
								+ String.join(",", project.getOs()));
					}
					//DB
					Row DBrow = sheet.getRow(16 + x);
					Cell DBCell = DBrow.getCell(7);
					if (project.getDb() == null) {
						DBCell.setCellValue("");
					} else {
						DBCell.setCellValue(String.join(",", project.getDb()));
					}
					//language
					Row LanguageRow = sheet.getRow(15 + x);
					Cell LanguageCell = LanguageRow.getCell(8);
					if (project.getLanguage() == null) {
						LanguageCell.setCellValue("");
					} else {
						LanguageCell.setCellValue(String.join(",", project.getLanguage()));
					}
					//Tool
					Row ToolRow = sheet.getRow(16 + x);
					Cell toolCell = ToolRow.getCell(8);
					if (project.getTool() == null) {
						toolCell.setCellValue("");
					} else {
						toolCell.setCellValue(String.join(",", project.getTool()));
					}
					//Type
					Row TypeRow = sheet.getRow(15 + x);
					Cell TypeCell = TypeRow.getCell(10);
					if (project.getProcesses() == null) {
						TypeCell.setCellValue("");
					} else {
						TypeCell.setCellValue(String.join(",", project.getProcesses()));
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
					Row ProjectNameRow = sheet.getRow(3 + x);
					Cell ProjectNameCell = ProjectNameRow.getCell(5);
					ProjectNameCell.setCellValue(project.getProjectName());
					//プロジェクト開始日
					Row StarDateRow = sheet.getRow(3 + x);
					Cell StarDateCell = StarDateRow.getCell(1);
					StarDateCell.setCellValue(project.getStartDate());
					//プロジェクト終了日
					Row EndDateRow = sheet.getRow(4 + x);
					Cell EndDateCell = EndDateRow.getCell(1);
					EndDateCell.setCellValue(project.getEndDate());
					//開発期間（mヶ月）
					Row monthsBetweenRow = sheet.getRow(3 + x);
					Cell monthsBetweenCell = monthsBetweenRow.getCell(4);
					monthsBetween = ChronoUnit.MONTHS.between(project.getStartDate(), project.getEndDate());
					if(monthsBetween == 0) {
						monthsBetween = 1;
					}
					monthsBetweenCell.setCellValue(monthsBetween + "ヶ月");
					sum =  sum + monthsBetween;
					//作業場所
					Row LocationRow = sheet.getRow(4 + x);
					Cell LocationCell = LocationRow.getCell(5);
					LocationCell.setCellValue(project.getLocation());
					//HW/OS
					Row HWDBRow = sheet.getRow(3 + x);
					Cell HWDBCell = HWDBRow.getCell(7);
					if (project.getHw() == null || project.getOs() == null) {
						HWDBCell.setCellValue("");
					} else {
						HWDBCell.setCellValue(String.join(",", project.getHw()) + ","
								+ String.join(",", project.getOs()));
					}
					//DB
					Row DBRow = sheet.getRow(4 + x);
					Cell DBCell = DBRow.getCell(7);
					if (project.getDb() == null) {
						DBCell.setCellValue("");
					} else {
						DBCell.setCellValue(String.join(",", project.getDb()));
					}
					//language
					Row LanguageRow = sheet.getRow(3 + x);
					Cell LanguageCell = LanguageRow.getCell(8);
					if (project.getLanguage() == null) {
						LanguageCell.setCellValue("");
					} else {
						LanguageCell.setCellValue(String.join(",", project.getLanguage()));
					}
					//Tool
					Row ToolRow = sheet.getRow(4 + x);
					Cell ToolCell = ToolRow.getCell(8);
					if (project.getTool() == null) {
						ToolCell.setCellValue("");
					} else {
						ToolCell.setCellValue(String.join(",", project.getTool()));
					}
					//Type
					Row TypeRow = sheet.getRow(3 + x);
					Cell TypeCell = TypeRow.getCell(10);
					if (project.getProcesses() == null) {
						TypeCell.setCellValue("");
					} else {
						TypeCell.setCellValue(String.join(",", project.getProcesses()));
					}
					//row,listindexインクリメント
					x += 2;
					listIndex++;
				}
			}
			
			//経験年数を算出しExcelに出力
			sheet = workbook.getSheetAt(0); // 1つ目のシートを取得
			Row experienceRow = sheet.getRow(3);
			Cell experienceCell = experienceRow.getCell(8);
			if(sum/12>=1) {
				experienceCell.setCellValue(sum / 12 + "年" + sum % 12 + "ヶ月" );
			}else {
				experienceCell.setCellValue(sum + "ヶ月" );
			}
			// Excelファイルのダウンロードのためのレスポンスの設定
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\"outputSkillSheet.xlsx\"");

			// 出力用のストリームを用意
			try (ServletOutputStream out = response.getOutputStream()) {
				workbook.write(out); // 変更を保存
			}
		}
	}
}