document.addEventListener('DOMContentLoaded', function () {
    let selectedSkills = {}; // { sectionIndex: [skills] }
    let selectedProcesses = {}; // { sectionIndex: [processes] }

    // 초기 섹션 수를 1로 설정 (0번 섹션이 이미 존재)
    let sectionCount = 1;

    function openSkillPopup(sectionIndex) {
        const skillPopup = document.getElementById('skill-popup');
        skillPopup.dataset.sectionIndex = sectionIndex;
        skillPopup.style.display = 'block';

        // 팝업 열릴 때 현재 섹션의 스킬을 표시
        const currentSkills = selectedSkills[sectionIndex] || [];
        document.querySelectorAll('#skill-popup .skill-chip').forEach(chip => {
            const skillName = chip.textContent.trim();
            if (currentSkills.includes(skillName)) {
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
        const sectionIndex = document.getElementById('skill-popup').dataset.sectionIndex;

        if (!selectedSkills[sectionIndex]) {
            selectedSkills[sectionIndex] = [];
        }

        if (!selectedSkills[sectionIndex].includes(skillName)) {
            selectedSkills[sectionIndex].push(skillName);
            skillElement.classList.add('selected');
        } else {
            selectedSkills[sectionIndex] = selectedSkills[sectionIndex].filter(skill => skill !== skillName);
            skillElement.classList.remove('selected');
        }
    }

    function selectProcess(processElement, sectionIndex) {
        const processName = processElement.textContent.trim();

        if (!selectedProcesses[sectionIndex]) {
            selectedProcesses[sectionIndex] = [];
        }

        if (!selectedProcesses[sectionIndex].includes(processName)) {
            selectedProcesses[sectionIndex].push(processName);
            processElement.classList.add('selected');
        } else {
            selectedProcesses[sectionIndex] = selectedProcesses[sectionIndex].filter(process => process !== processName);
            processElement.classList.remove('selected');
        }
    }

    function applySelectedSkills() {
        const skillPopup = document.getElementById('skill-popup');
        const sectionIndex = skillPopup.dataset.sectionIndex;
        const selectedSkillsContainer = document.getElementById(`selected-skills-${sectionIndex}`);
        const hiddenSkillsInput = document.getElementById(`projects[${sectionIndex}].skills`);
        selectedSkillsContainer.innerHTML = '';

        if (selectedSkills[sectionIndex]) {
            selectedSkills[sectionIndex].forEach(skillName => {
                const skillTag = document.createElement('span');
                skillTag.className = 'skill-chip selected';
                skillTag.textContent = skillName;
                selectedSkillsContainer.appendChild(skillTag);
            });

            hiddenSkillsInput.value = selectedSkills[sectionIndex].join(',');
        } else {
            hiddenSkillsInput.value = '';
        }

        closeSkillPopup();
    }

    function addExperienceSection() {
        console.log('addExperienceSection called'); // 디버깅용 로그
        const experienceContainer = document.getElementById('experience-sections');
        const newSection = document.querySelector('.experience').cloneNode(true);

        // 새로운 섹션의 인덱스 설정
        const currentIndex = sectionCount;
        sectionCount++;

        // 입력 필드의 name 속성 업데이트
        const inputs = newSection.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            const name = input.getAttribute('name');
            if (name) {
                const updatedName = name.replace(/\d+/, currentIndex);
                input.setAttribute('name', updatedName);
                if (input.type === 'hidden' || input.tagName.toLowerCase() === 'textarea') {
                    input.value = '';
                } else {
                    input.value = '';
                }
            }
        });

        // 새로운 섹션의 ID 및 숨겨진 필드 초기화
        const selectedSkillsContainer = newSection.querySelector('.skill-display');
        selectedSkillsContainer.id = `selected-skills-${currentIndex}`;
        selectedSkillsContainer.innerHTML = '';

        const hiddenSkillsInput = newSection.querySelector('input[type="hidden"][name^="projects"]');
        hiddenSkillsInput.id = `projects[${currentIndex}].skills`;
        hiddenSkillsInput.value = '';

        const hiddenProcessesInput = newSection.querySelector('input[type="hidden"][name$=".processes"]');
        hiddenProcessesInput.id = `projects[${currentIndex}].processes`;
        hiddenProcessesInput.value = '';

        // 프로세스 칩 초기화 및 이벤트 리스너 설정
        const processChips = newSection.querySelectorAll('.process-chip');
        processChips.forEach(chip => {
            chip.classList.remove('selected');
            chip.onclick = function () {
                selectProcess(chip, currentIndex);
            };
        });

        // 스킬 칩 초기화 및 이벤트 리스너 설정
        newSection.querySelectorAll('.skill-chip').forEach(chip => {
            chip.classList.remove('selected');
            chip.onclick = function () {
                const category = chip.parentElement.previousElementSibling.textContent.trim().toLowerCase();
                selectSkill(chip, category);
            };
        });

        // 스킬 추가 버튼 이벤트 리스너 설정
        const skillAddButton = newSection.querySelector('.skill-add-button');
        skillAddButton.onclick = function () {
            openSkillPopup(currentIndex);
        };

        // 프로세스 칩의 ID 업데이트
        newSection.querySelector('.process-chips').id = `process-chips-${currentIndex}`;

        experienceContainer.appendChild(newSection);
    }

    // 초기 섹션의 이벤트 리스너 설정
    document.querySelectorAll('.skill-chip').forEach(chip => {
        chip.addEventListener('click', function () {
            const category = chip.parentElement.previousElementSibling.textContent.trim().toLowerCase();
            selectSkill(chip, category);
        });
    });

    document.querySelectorAll('.process-chip').forEach(chip => {
        const sectionIndex = chip.parentElement.id.split('-').pop();
        chip.addEventListener('click', function () {
            selectProcess(chip, sectionIndex);
        });
    });

    // 팝업의 적용 버튼 이벤트 리스너 설정
    document.querySelector('.apply-button').addEventListener('click', applySelectedSkills);

    // "経歴追加" 버튼에 이벤트 리스너 바인딩
    document.querySelector('.history-add-button').addEventListener('click', addExperienceSection);

    // 함수들을 글로벌 스코프로 노출
    window.openSkillPopup = openSkillPopup;
    window.closeSkillPopup = closeSkillPopup;
    window.selectSkill = selectSkill;
    window.selectProcess = selectProcess;
    window.applySelectedSkills = applySelectedSkills;
});
