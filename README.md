## 経歴書システム

### 概要
社内で経歴書の作成.編集.管理をシステム化し業務を簡素化するシステムです。

### 主な機能
- ユーザーのログイン・登録機能
- 管理者によるユーザー管理
- 経歴書の作成&編集
- 経歴書のExcel出力機能

### 環境構築
1. **実行環境**
   - Java 17 以上
   - PostgreSQL
   - Springboot framework 3.x
   - Thymeleaf 3.x

2. **プロジェクトのクローン**
   ```bash
   https://github.com/rfujii1113/resumeSystem.git
  
### 計画書のスキル追加のため必要なDDLのPath(スキル追加機能未実装)
resources/data/skillMaster.sql

### DB,画面設計

| ER図 | UI(wire frame) | UI(Mock) |
| --- | --- | --- |
| <img src="https://github.com/user-attachments/assets/f0a52422-1c39-4e35-a517-29728f86c438" width="200" alt="ER図"> | <img src="https://github.com/user-attachments/assets/dbf47816-e05f-4038-bb25-c2cd937b9df7" width="200" alt="wire frame"> | <img src="https://github.com/user-attachments/assets/6a1bb308-deed-42d5-afaf-d979755ee6bd" width="200" alt="ui"> |
