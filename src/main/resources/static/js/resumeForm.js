document.addEventListener('DOMContentLoaded', function () {

    const categories = ['os', 'db', 'language', 'tool'];
    let selectedSkills = {}; // { sectionIndex: { os: [], db: [], language: [], tool: [] } }
    let sectionCount = 0; 

    // Proejct id 생성 함수
    function generateProjectId() {
        return 'projectId-' + Date.now() + '-' + Math.random().toString(10);
    }

    // 경력 추가 버튼 클릭 이벤트
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('history-add-button')) {
            addExperienceSection();
        }
    });

    // 스킬 추가 버튼 클릭 이벤트 (이벤트 위임 방식)
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('skill-add-button')) {
            const sectionIndex = event.target.getAttribute('data-index');
            openSkillPopup(sectionIndex);
        }
    });

    function addExperienceSection() {
        const experienceContainer = document.getElementById('experience-sections');

        // 새로운 섹션 HTML 구조
        const newSection = document.createElement('section');
        newSection.classList.add('experience-item');
        newSection.dataset.index = sectionCount;

        // 새로운 project id 생성
        const newProjectId = generateProjectId();

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
            <!-- 고유 ssid 숨겨진 필드 -->
            <input type="hidden" name="projects[${sectionCount}].ssid" value="${newProjectId}" />
            
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
        // 팝업 열기 로직 추가
        console.log(`Opening skill popup for section: ${sectionIndex}`);
    }

});
