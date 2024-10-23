document.addEventListener('DOMContentLoaded', function () {

    const categories = ['os', 'db', 'language', 'tool']; 
    let selectedSkills = {}; // { sectionIndex: { os: [], db: [], language: [], tool: [] } }
    let selectedProcesses = {}; // { sectionIndex: [processes] }
    let sectionCount = document.querySelectorAll('.experience-item').length;

    // 이벤트 위임 방식으로 스킬 추가 버튼에 이벤트 리스너 등록
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('skill-add-button')) {
            const sectionIndex = event.target.closest('.experience-item').dataset.index;
            openSkillPopup(sectionIndex);
        }
    });

    // 이벤트 위임 방식으로 경력 추가 버튼에 이벤트 리스너 등록
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('history-add-button')) {
            addExperienceSection();
        }
    });

    function openSkillPopup(sectionIndex) {
        const skillPopup = document.getElementById('skill-popup');
        skillPopup.dataset.sectionIndex = sectionIndex;
        skillPopup.style.display = 'block';

        // 팝업에서 해당 섹션의 현재 스킬 표시
        const currentSkills = selectedSkills[sectionIndex] || { os: [], db: [], language: [], tool: [] };
        document.querySelectorAll('#skill-popup .skill-chip').forEach(chip => {
            const skillName = chip.textContent.trim();
            const category = chip.getAttribute('data-category');
            if (currentSkills[category].includes(skillName)) {
                chip.classList.add('selected');
            } else {
                chip.classList.remove('selected');
            }
        });
    }

    function closeSkillPopup() {
        document.getElementById('skill-popup').style.display = 'none';
    }

    function selectSkill(skillElement, category) {
        const skillName = skillElement.textContent.trim();
        const skillPopup = document.getElementById('skill-popup');
        const sectionIndex = skillPopup.dataset.sectionIndex;

        if (!selectedSkills[sectionIndex]) {
            selectedSkills[sectionIndex] = { os: [], db: [], language: [], tool: [] };
        }

        if (!selectedSkills[sectionIndex][category].includes(skillName)) {
            selectedSkills[sectionIndex][category].push(skillName);
            skillElement.classList.add('selected');
        } else {
            selectedSkills[sectionIndex][category] = selectedSkills[sectionIndex][category].filter(skill => skill !== skillName);
            skillElement.classList.remove('selected');
        }
    }

    function applySelectedSkills() {
        const skillPopup = document.getElementById('skill-popup');
        const sectionIndex = skillPopup.dataset.sectionIndex;

        // 각 카테고리에 대한 숨은 입력값 업데이트
        categories.forEach(category => {
            const hiddenSkillsInput = document.querySelector(`input[name="projects[${sectionIndex}].${category}"]`);
            if (hiddenSkillsInput) {
                hiddenSkillsInput.value = selectedSkills[sectionIndex][category].join(',');
            }
        });

        // 스킬 디스플레이 업데이트
        updateSkillDisplay(sectionIndex);

        closeSkillPopup();
    }

    function updateSkillDisplay(sectionIndex) {
        categories.forEach(category => {
            const selectedSkillsContainer = document.querySelector(`#selected-skills-${sectionIndex}-${category}`);
            if (selectedSkillsContainer) {
                selectedSkillsContainer.innerHTML = '';

                // 선택된 스킬 추가
                selectedSkills[sectionIndex][category].forEach(skillName => {
                    const skillTag = document.createElement('span');
                    skillTag.className = 'skill-chip selected';
                    skillTag.textContent = skillName;
                    selectedSkillsContainer.appendChild(skillTag);
                });
            }
        });
    }

    function addExperienceSection() {
        const experienceContainer = document.getElementById('experience-sections');
        const templateSection = document.querySelector('.experience-item');
        const newSection = templateSection.cloneNode(true);

        // 현재 인덱스 설정
        const currentIndex = sectionCount;
        sectionCount++;

        // 입력 필드 업데이트
        const inputs = newSection.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            let name = input.getAttribute('name');
            let id = input.getAttribute('id');
            if (name) {
                name = name.replace(/\[\d+\]/g, `[${currentIndex}]`);
                input.setAttribute('name', name);
                // 값 초기화
                if (input.type === 'checkbox' || input.type === 'radio') {
                    input.checked = false;
                } else {
                    input.value = '';
                }
            }
            if (id) {
                id = id.replace(/\d+/, currentIndex);
                input.setAttribute('id', id);
            }
        });

        // 섹션 제목 업데이트
        const sectionTitle = newSection.querySelector('h3 span');
        if (sectionTitle) {
            sectionTitle.textContent = currentIndex + 1; // 인덱스는 0부터 시작하므로 +1
        }

        // 스킬 디스플레이 초기화
        categories.forEach(category => {
            const selectedSkillsContainer = newSection.querySelector(`#selected-skills-${currentIndex}-${category}`);
            if (selectedSkillsContainer) {
                selectedSkillsContainer.innerHTML = '';
            }
        });

        // 새 섹션 추가
        experienceContainer.appendChild(newSection);

        // 새 섹션에 대한 selectedSkills 초기화
        selectedSkills[currentIndex] = { os: [], db: [], language: [], tool: [] };
        selectedProcesses[currentIndex] = [];
    }

    // 팝업 내 스킬 칩 클릭 이벤트 설정 (이벤트 위임 방식 사용)
    document.querySelectorAll('#skill-popup .skill-chip').forEach(chip => {
        chip.onclick = function () {
            const category = chip.getAttribute('data-category');
            selectSkill(chip, category);
        };
    });

    // '적용' 버튼 클릭 이벤트 리스너 설정
    document.querySelector('.apply-button').addEventListener('click', applySelectedSkills);

    // 팝업 닫기 버튼 이벤트 리스너 설정
    document.querySelector('.close-button').addEventListener('click', closeSkillPopup);
});
