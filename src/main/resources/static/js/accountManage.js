  // selectTypeの選択に応じて、入力フィールドを動的に更新
        function updateInputField() {
            var selectType = document.getElementById("selectType").value;
            var inputField = document.getElementById("dynamicInput");

            // selectTypeの値に応じてinputフィールドのtypeとnameを変更
            if (selectType === "empId") {
                inputField.type = "number";
                inputField.name = "empId";
                inputField.placeholder = "社員番号";
            } else if (selectType === "empName") {
                inputField.type = "text";
                inputField.name = "empName";
                inputField.placeholder = "社員名";
            }
        }

        // ページ読み込み時に実行して、初期状態を設定
        window.onload = function() {
            updateInputField();
        };