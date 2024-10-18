// src/main/resources/static/js/resumeForm.js

document.addEventListener('DOMContentLoaded', function () {

    let selectedSkills = {}; // { sectionIndex: { os: [], db: [], language: [], tool: [] } }
    let selectedProcesses = {}; // { sectionIndex: [processes] }

    // 초기 섹션 수를 .experience-item의 개수로 설정
    let sectionCount = document.querySelectorAll('.experience-item').length; 

    // 기존 폼 데이터 기반으로 selectedSkills와 selectedProcesses 초기화
    document.querySelectorAll('.experience-item').forEach((section, index) => {
        // 각 카테고리에 대한 selectedSkills 초기화
        selectedSkills[index] = { os: [], db: [], language: [], tool: [] };

        ['os', 'db', 'language', 'tool'].forEach(category => {
            const skillsInput = section.querySelector(`input[name="projects[${index}].skills.${category}"]`);
            if (skillsInput && skillsInput.value) {
                selectedSkills[index][category] = skillsInput.value.split(',');
                selectedSkills[index][category].forEach(skill => {
                    const skillChips = section.querySelectorAll(`.skill-chip`);
                    const skillChip = Array.from(skillChips).find(chip => chip.textContent.trim() === skill);
                    if (skillChip) {
                        skillChip.classList.add('selected');
                    }
                });
            }
        });

        // selectedProcesses 초기화
        const processesInput = section.querySelector(`input[name="projects[${index}].processes"]`);
        if (processesInput && processesInput.value) {
            selectedProcesses[index] = processesInput.value.split(',');
            selectedProcesses[index].forEach(process => {
                const processChips = section.querySelectorAll(`.process-chip`);
                const processChip = Array.from(processChips).find(chip => chip.textContent.trim() === process);
                if (processChip) {
                    processChip.classList.add('selected');
                }
            });
        }

        // 프로세스 칩 이벤트 리스너 설정
        const processChips = section.querySelectorAll('.process-chip');
        processChips.forEach(chip => {
            chip.onclick = function () {
                selectProcess(chip, index);
            };
        });

        // 스킬 추가 버튼 이벤트 리스너 설정
        const skillAddButton = section.querySelector('.skill-add-button');
        if (skillAddButton) {
            skillAddButton.onclick = function () {
                openSkillPopup(index);
            };
        }
    });

    function openSkillPopup(sectionIndex) {
        const skillPopup = document.getElementById('skill-popup');
        skillPopup.dataset.sectionIndex = sectionIndex;
        skillPopup.style.display = 'block';

        // 해당 섹션의 현재 스킬 표시
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
        ['os', 'db', 'language', 'tool'].forEach(category => {
            const hiddenSkillsInput = document.querySelector(`input[name="projects[${sectionIndex}].skills.${category}"]`);
            if (hiddenSkillsInput) {
                hiddenSkillsInput.value = selectedSkills[sectionIndex][category].join(',');
            }

            // 스킬 디스플레이 업데이트
            const selectedSkillsContainer = document.querySelector(`#selected-skills-${sectionIndex}-${category}`);
            if (selectedSkillsContainer) {
                // 기존 표시된 스킬 초기화
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

        closeSkillPopup();
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

        // 숨은 입력값 업데이트
        const hiddenProcessesInput = document.querySelector(`input[name="projects[${sectionIndex}].processes"]`);
        if (hiddenProcessesInput) {
            hiddenProcessesInput.value = selectedProcesses[sectionIndex].join(',');
        }
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

        // 클론된 섹션 내의 모든 ID 업데이트
        const elementsWithId = newSection.querySelectorAll('[id]');
        elementsWithId.forEach(element => {
            let id = element.getAttribute('id');
            if (id) {
                id = id.replace(/\d+/, currentIndex);
                element.setAttribute('id', id);
            }
        });

        // 프로세스 칩 이벤트 리스너 설정
        const processChips = newSection.querySelectorAll('.process-chip');
        processChips.forEach(chip => {
            chip.classList.remove('selected');
            chip.onclick = function () {
                selectProcess(chip, currentIndex);
            };
        });

        // 스킬 추가 버튼 이벤트 리스너 설정
        const skillAddButton = newSection.querySelector('.skill-add-button');
        if (skillAddButton) {
            skillAddButton.onclick = function () {
                openSkillPopup(currentIndex);
            };
        }

        // 책임사항 텍스트 영역 초기화
        const responsibilityTextarea = newSection.querySelector('textarea[name$=".responsibility"]');
        if (responsibilityTextarea) {
            responsibilityTextarea.value = '';
        }

        // 새 섹션 추가
        experienceContainer.appendChild(newSection);

        // 새 섹션에 대한 selectedSkills와 selectedProcesses 초기화
        selectedSkills[currentIndex] = { os: [], db: [], language: [], tool: [] };
        selectedProcesses[currentIndex] = [];
    }

    // 스킬 팝업 내 스킬 칩 이벤트 리스너 설정
    document.querySelectorAll('#skill-popup .skill-chip').forEach(chip => {
        chip.onclick = function () {
            const category = chip.getAttribute('data-category');
            selectSkill(chip, category);
        };
    });

    // 팝업 적용 버튼 이벤트 리스너 설정
    document.querySelector('.apply-button').addEventListener('click', applySelectedSkills);

    // "경력 추가" 버튼 이벤트 리스너 설정
    document.querySelector('.history-add-button').addEventListener('click', addExperienceSection);

    // 전역 범위에 함수 노출 (필요 시)
    window.openSkillPopup = openSkillPopup;
    window.closeSkillPopup = closeSkillPopup;
    window.selectSkill = selectSkill;
    window.selectProcess = selectProcess;
    window.applySelectedSkills = applySelectedSkills;
    window.addExperienceSection = addExperienceSection;
});
