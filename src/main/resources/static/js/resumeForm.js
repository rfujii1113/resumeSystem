document.addEventListener('DOMContentLoaded', function () {

    let sectionCount = 1; // 最初のセクションはすでにHTMLに追加されているため、1から開始
    let selectedSkills = {}; // 各プロジェクトセクションごとのスキルを保存 {sectionIndex: {os: [], db: [], language: [], tool: []}}
    let selectedProcesses = {}; // 各プロジェクトセクションごとの選択されたプロセスを保存 {sectionIndex: []}

    // Project ID生成関数
    function generateProjectId() {
        return 'projectId-' + Date.now().toString(36) + '-' + Math.random().toString(36).slice(2, 12);
    }

    // 最初のプロジェクトセクションのProject IDを生成
    const firstProjectIdField = document.getElementById('projects[0].projectId');
    if (!firstProjectIdField.value) {
        firstProjectIdField.value = generateProjectId();
    }

    // 経歴追加ボタンクリックイベント
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('history-add-button')) {
            addExperienceSection();
        }
    });

    // 経歴セクション追加関数
    function addExperienceSection() {
        const experienceContainer = document.getElementById('experience-sections');

        // 新しいセクションのHTML構造
        const newSection = document.createElement('section');
        newSection.classList.add('experience-item');
        newSection.dataset.index = sectionCount;

        // 新しいProject IDを生成
        const newProjectId = generateProjectId();
        console.log(`New project id: ${newProjectId}`);

        newSection.innerHTML = `
            <h3>プロジェクト <span>${sectionCount + 1}</span></h3>
            <div class="input-group">
                <label>
                    プロジェクト名
                    <input type="text" name="projects[${sectionCount}].projectName" placeholder="" />
                </label>
                <label>
                    作業場所
                    <input type="text" name="projects[${sectionCount}].location" placeholder="" />
                </label>
                <label>
                    開始日
                    <input type="date" name="projects[${sectionCount}].startDate" required />
                </label>
                <label>
                    終了日
                    <input type="date" name="projects[${sectionCount}].endDate" required />
                </label>
            </div>
            <!-- 生成されたProject IDを隠しフィールドに保存 -->
            <input type="hidden" name="projects[${sectionCount}].projectId" value="${newProjectId}" />

            <!-- Process選択 -->
            <div class="input-group">
                <label>
                    担当したプロセス
                    <div class="process-chips" id="process-chips-${sectionCount}">
                        <span class="process-chip">調査分析</span>
                        <span class="process-chip">設計</span>
                        <span class="process-chip">開発</span>
                        <span class="process-chip">テスト</span>
                        <span class="process-chip">運用保守</span>
                    </div>
                    <input type="hidden" name="projects[${sectionCount}].processes" id="projects[${sectionCount}].processes">
                </label>
            </div>

            <!-- Skill -->
            <div class="input-group tech">
                <label class="skill-label">
                    OS
                    <div class="selected-skills-list" id="selected-skills-${sectionCount}-os"></div>
                    <input type="hidden" name="projects[${sectionCount}].os" id="projects[${sectionCount}].os">
                </label>
                <label class="skill-label">
                    DB
                    <div class="selected-skills-list" id="selected-skills-${sectionCount}-db"></div>
                    <input type="hidden" name="projects[${sectionCount}].db" id="projects[${sectionCount}].db">
                </label>
                <label class="skill-label">
                    Language
                    <div class="selected-skills-list" id="selected-skills-${sectionCount}-language"></div>
                    <input type="hidden" name="projects[${sectionCount}].language" id="projects[${sectionCount}].language">
                </label>
                <label class="skill-label">
                    Tools
                    <div class="selected-skills-list" id="selected-skills-${sectionCount}-tool"></div>
                    <input type="hidden" name="projects[${sectionCount}].tool" id="projects[${sectionCount}].tool">
                </label>
                <button type="button" class="skill-add-button" data-index="${sectionCount}">スキル追加</button>
            </div>

            <div class="responsibility">
                <textarea name="projects[${sectionCount}].responsibility" placeholder="担当した業務を簡単に記入してください。" maxlength="500" style="overflow: hidden; resize: none;"></textarea>
            </div>
        `;

        // セクションを追加
        experienceContainer.appendChild(newSection);

        // 新しいセクションのselectedSkillsとselectedProcessesを初期化
        selectedSkills[sectionCount] = { os: [], db: [], language: [], tool: [] };
        selectedProcesses[sectionCount] = [];

        // セクションカウントを増加
        sectionCount++;
    }

    // Processチップクリックイベント処理
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('process-chip')) {
            const sectionIndex = event.target.closest('.experience-item').dataset.index;
            const processName = event.target.innerText;

            // 選択/解除スタイルを適用
            event.target.classList.toggle('selected');

            // selectedProcessesを更新
            if (!selectedProcesses[sectionIndex]) selectedProcesses[sectionIndex] = [];

            if (selectedProcesses[sectionIndex].includes(processName)) {
                // すでに選択されている場合は削除
                selectedProcesses[sectionIndex] = selectedProcesses[sectionIndex].filter(p => p !== processName);
            } else {
                // 選択されていない場合は追加
                selectedProcesses[sectionIndex].push(processName);
            }

            // 選択されたプロセスをhidden inputに保存
            document.getElementById(`projects[${sectionIndex}].processes`).value = selectedProcesses[sectionIndex].join(', ');
        }
    });

    // スキル選択ポップアップを開く
    function openSkillPopup(sectionIndex) {
        const popup = document.getElementById('skill-popup');
        popup.style.display = 'block';
        popup.dataset.sectionIndex = sectionIndex;

        // スキル選択を初期化（すべての選択されたスキルチップから 'selected' クラスを削除）
        const allSkillChips = document.querySelectorAll('.skill-chip');
        allSkillChips.forEach(chip => {
            chip.classList.remove('selected');
        });

        // すでに選択されているスキルがある場合、それらを再度選択状態で表示
        if (selectedSkills[sectionIndex]) {
            const selectedOsSkills = selectedSkills[sectionIndex].os;
            const selectedDbSkills = selectedSkills[sectionIndex].db;
            const selectedLanguageSkills = selectedSkills[sectionIndex].language;
            const selectedToolSkills = selectedSkills[sectionIndex].tool;

            // 各カテゴリーごとに選択されたスキルを再度選択状態で表示
            selectedOsSkills.forEach(skill => {
                const skillChip = document.querySelector(`.skill-chip[data-category="os"][th:text="${skill}"]`);
                if (skillChip) skillChip.classList.add('selected');
            });

            selectedDbSkills.forEach(skill => {
                const skillChip = document.querySelector(`.skill-chip[data-category="db"][th:text="${skill}"]`);
                if (skillChip) skillChip.classList.add('selected');
            });

            selectedLanguageSkills.forEach(skill => {
                const skillChip = document.querySelector(`.skill-chip[data-category="language"][th:text="${skill}"]`);
                if (skillChip) skillChip.classList.add('selected');
            });

            selectedToolSkills.forEach(skill => {
                const skillChip = document.querySelector(`.skill-chip[data-category="tool"][th:text="${skill}"]`);
                if (skillChip) skillChip.classList.add('selected');
            });
        }
    }

    // ポップアップを閉じるボタン
    document.querySelector('.close-button').addEventListener('click', function () {
        document.getElementById('skill-popup').style.display = 'none';
    });

    // スキル選択チップイベント処理
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('skill-chip')) {
            toggleSkillSelection(event.target);
        }
    });

    // スキル選択/解除処理関数
    function toggleSkillSelection(skillChip) {
        const skillCategory = skillChip.getAttribute('data-category');
        const skillName = skillChip.innerText;

        // 選択/解除スタイルを適用
        skillChip.classList.toggle('selected');
    }

    // スキル追加ボタンクリックイベント（イベント委任方式）
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('skill-add-button')) {
            const sectionIndex = event.target.getAttribute('data-index');
            openSkillPopup(sectionIndex);
        }
    });

    // ポップアップの適用ボタンクリックイベント
    document.querySelector('.apply-button').addEventListener('click', function () {
        const sectionIndex = document.getElementById('skill-popup').dataset.sectionIndex;

        // 選択されたスキルを抽出
        const selectedChips = document.querySelectorAll('.skill-chip.selected');
        const selectedOsSkills = [];
        const selectedDbSkills = [];
        const selectedLanguageSkills = [];
        const selectedToolSkills = [];

        selectedChips.forEach(chip => {
            const category = chip.getAttribute('data-category');
            const skillName = chip.innerText;

            if (category === 'os') {
                selectedOsSkills.push(skillName);
            } else if (category === 'db') {
                selectedDbSkills.push(skillName);
            } else if (category === 'language') {
                selectedLanguageSkills.push(skillName);
            } else if (category === 'tool') {
                selectedToolSkills.push(skillName);
            }
        });

        // selectedSkills[sectionIndex]が存在するか確認し、なければ初期化
        if (!selectedSkills[sectionIndex]) {
            selectedSkills[sectionIndex] = { os: [], db: [], language: [], tool: [] };
        }

        // 選択されたスキルを対応するセクションのselectedSkillsオブジェクトに保存
        selectedSkills[sectionIndex].os = selectedOsSkills;
        selectedSkills[sectionIndex].db = selectedDbSkills;
        selectedSkills[sectionIndex].language = selectedLanguageSkills;
        selectedSkills[sectionIndex].tool = selectedToolSkills;

        // 選択されたスキルを対応するセクションのHTMLに追加
        document.getElementById(`selected-skills-${sectionIndex}-os`).innerHTML = selectedOsSkills.join(', ');
        document.getElementById(`projects[${sectionIndex}].os`).value = selectedOsSkills.join(', ');

        document.getElementById(`selected-skills-${sectionIndex}-db`).innerHTML = selectedDbSkills.join(', ');
        document.getElementById(`projects[${sectionIndex}].db`).value = selectedDbSkills.join(', ');

        document.getElementById(`selected-skills-${sectionIndex}-language`).innerHTML = selectedLanguageSkills.join(', ');
        document.getElementById(`projects[${sectionIndex}].language`).value = selectedLanguageSkills.join(', ');

        document.getElementById(`selected-skills-${sectionIndex}-tool`).innerHTML = selectedToolSkills.join(', ');
        document.getElementById(`projects[${sectionIndex}].tool`).value = selectedToolSkills.join(', ');

        // ポップアップを閉じる
        document.getElementById('skill-popup').style.display = 'none';
    });

});
