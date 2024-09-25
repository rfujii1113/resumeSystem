# 経歴書システム (Resyume System)

## 概要
本システムは社内で経歴書の作成.編集.管理をシステム化し業務を簡素化します

## 主な機能
- ユーザーのログイン・登録機能
- 管理者によるユーザー管理
- 経歴書の作成編集
- 経歴書のPDF出力
- 経歴書検索

## 環境構築
1. **必要なツール**
   - Java 11 以上
   - PostgreSQL
   - Eclipse IDE
   - Spring Framework 5.x
   - Thymeleaf 3.x

2. **プロジェクトのクローン**
   ```bash
   https://github.com/rfujii1113/resumeSystem.git
   ```

3. **Eclipseでプロジェクトをインポート**
   - `File` -> `Import` -> `Existing Maven Projects` を選択し、プロジェクトのフォルダを選びます。

4. **データベース設定**
   - `src/main/resources/application.properties` にPostgreSQLの接続情報を設定します。
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/testdb
   spring.datasource.username=postgres
   spring.datasource.password=root
   spring.datasource.driver-class-name=org.postgresql.Driver
   ```

5. **アプリケーションの起動**
   - Eclipseからプロジェクトを実行し、`http://localhost:8080` にアクセスして動作を確認します。
  
## データベース設計
このシステムでは、以下のようなテーブルを使用します

### EMPLOYEEテーブル
社員番号					empId					integer	
社員名					empName					VARCHAR(255)	
性別					gender					VARCHAR(255)	
生年月日					birthDate					date　	
現住所					currentAddress					VARCHAR(255)	
本籍					permanentAddress					VARCHAR(255)	
配偶者					spouse					boolean	
最終学校					lastSchool					VARCHAR(255)	
専攻学科					major					VARCHAR(255)	
卒業年月					graduationDate					date	
教育・研修					educationTraining					VARCHAR(255)	
最寄り駅					nearestStation					VARCHAR(255)	
権限区分					permission					integer	
（部署）					department					VARCHAR(255)	
パスワード					password					VARCHAR(255)	
削除フラグ					deleteFlag					integer	
![image](https://github.com/user-attachments/assets/a747bbbd-d680-4c30-a962-0d31b4aab674)

### M_SKILLテーブル
スキルid					skillId					integer	
スキル名					skillName					VARCHAR(255)	
スキル区分					skillCategory					VARCHAR(255)	
![image](https://github.com/user-attachments/assets/47c97283-4da0-4327-8dc5-cdf34ef0e7b2)

### CARRERテーブル
プロジェクトid					projectId					VARCHAR(255)	
開発期間（開始）					startDate					date	
開発期間（終了）					endDate					date	
プロジェクト名					projectName					VARCHAR(255)	
作業場所					workLocation					VARCHAR(255)	
HW					hardware					VARCHAR(255)	
OS					os					VARCHAR(255)	
DB					database					VARCHAR(255)	
言語					language					VARCHAR(255)	
ツール					tools					VARCHAR(255)	
担当					responsibility					VARCHAR(255)	
社員ID					empId					integer	
備考					remarks					VARCHAR(255)	
削除フラグ					deleteFlag					integer	
業務フラグ					workFlag					integer	
![image](https://github.com/user-attachments/assets/7ae85655-0a38-413b-9f45-526e96b039bc)

