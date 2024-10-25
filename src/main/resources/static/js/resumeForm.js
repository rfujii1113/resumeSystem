document.addEventListener('DOMContentLoaded', function () {

    let sectionCount = 1; // 첫 번째 섹션은 이미 HTML에 추가된 상태이므로 1로 시작
    let selectedSkills = {}; // 각 프로젝트 섹션별로 스킬을 저장 {sectionIndex: {os: [], db: [], language: [], tool: []}}

    // Project ID 생성 함수
    function generateProjectId() {
        return 'projectId-' + Date.now().toString(36) + '-' + Math.random().toString(36).slice(2, 12);
    }

    // 경력 추가 버튼 클릭 이벤트
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('history-add-button')) {
            addExperienceSection();
        }
    });

    // 경력 섹션 추가 함수
    function addExperienceSection() {
        const experienceContainer = document.getElementById('experience-sections');

        // 새로운 섹션 HTML 구조
        const newSection = document.createElement('section');
        newSection.classList.add('experience-item');
        newSection.dataset.index = sectionCount;

        // 새로운 Project ID 생성
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
            <!-- 생성된 Project ID가 숨겨진 필드 -->
            <input type="hidden" name="projects[${sectionCount}].projectId" value="${newProjectId}" />
            
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

        // 섹션 추가
        experienceContainer.appendChild(newSection);

        // 새 섹션에 대한 selectedSkills 초기화
        selectedSkills[sectionCount] = { os: [], db: [], language: [], tool: [] };

        // 섹션 카운트 증가
        sectionCount++;
    }

    // 스킬 선택 팝업 열기
    function openSkillPopup(sectionIndex) {
        const popup = document.getElementById('skill-popup');
        popup.style.display = 'block';
        popup.dataset.sectionIndex = sectionIndex;

        // 스킬 선택 초기화 (모든 선택된 스킬 칩에서 'selected' 클래스를 제거)
        const allSkillChips = document.querySelectorAll('.skill-chip');
        allSkillChips.forEach(chip => {
            chip.classList.remove('selected');
        });

        // 선택된 스킬이 이미 있는 경우, 해당 스킬들을 다시 선택 상태로 표시
        if (selectedSkills[sectionIndex]) {
            const selectedOsSkills = selectedSkills[sectionIndex].os;
            const selectedDbSkills = selectedSkills[sectionIndex].db;
            const selectedLanguageSkills = selectedSkills[sectionIndex].language;
            const selectedToolSkills = selectedSkills[sectionIndex].tool;

            // 각 카테고리별로 선택된 스킬들을 다시 선택 상태로 표시
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

    // 팝업 닫기 버튼
    document.querySelector('.close-button').addEventListener('click', function () {
        document.getElementById('skill-popup').style.display = 'none';
    });

    // 스킬 선택 칩 이벤트 처리
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('skill-chip')) {
            toggleSkillSelection(event.target);
        }
    });

    // 스킬 선택/해제 처리 함수
    function toggleSkillSelection(skillChip) {
        const skillCategory = skillChip.getAttribute('data-category');
        const skillName = skillChip.innerText;

        // 선택/해제 스타일 적용
        skillChip.classList.toggle('selected');
    }

    // 스킬 추가 버튼 클릭 이벤트 (이벤트 위임 방식)
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('skill-add-button')) {
            const sectionIndex = event.target.getAttribute('data-index');
            openSkillPopup(sectionIndex);
        }
    });

    // 팝업의 적용 버튼 클릭 이벤트
    document.querySelector('.apply-button').addEventListener('click', function () {
        const sectionIndex = document.getElementById('skill-popup').dataset.sectionIndex;

        // 선택된 스킬들을 추출
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

        // selectedSkills[sectionIndex]가 존재하는지 확인하고, 없으면 초기화
        if (!selectedSkills[sectionIndex]) {
            selectedSkills[sectionIndex] = { os: [], db: [], language: [], tool: [] };
        }

        // 선택한 스킬을 해당 섹션의 selectedSkills 객체에 저장
        selectedSkills[sectionIndex].os = selectedOsSkills;
        selectedSkills[sectionIndex].db = selectedDbSkills;
        selectedSkills[sectionIndex].language = selectedLanguageSkills;
        selectedSkills[sectionIndex].tool = selectedToolSkills;

        // 선택한 스킬을 해당 섹션의 HTML에 추가
        document.getElementById(`selected-skills-${sectionIndex}-os`).innerHTML = selectedOsSkills.join(', ');
        document.getElementById(`projects[${sectionIndex}].os`).value = selectedOsSkills.join(', ');

        document.getElementById(`selected-skills-${sectionIndex}-db`).innerHTML = selectedDbSkills.join(', ');
        document.getElementById(`projects[${sectionIndex}].db`).value = selectedDbSkills.join(', ');

        document.getElementById(`selected-skills-${sectionIndex}-language`).innerHTML = selectedLanguageSkills.join(', ');
        document.getElementById(`projects[${sectionIndex}].language`).value = selectedLanguageSkills.join(', ');

        document.getElementById(`selected-skills-${sectionIndex}-tool`).innerHTML = selectedToolSkills.join(', ');
        document.getElementById(`projects[${sectionIndex}].tool`).value = selectedToolSkills.join(', ');

        // 팝업 닫기
        document.getElementById('skill-popup').style.display = 'none';
    });

});
