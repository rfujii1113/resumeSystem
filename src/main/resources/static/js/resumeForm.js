document.addEventListener('DOMContentLoaded', function () {
    let selectedSkills = {}; // { sectionIndex: [skills] }
    let selectedProcesses = {}; // { sectionIndex: [processes] }
    let sectionCount = 1; // Initialize section count to 1 (section 0 already exists)

    function openSkillPopup(sectionIndex) {
        const skillPopup = document.getElementById('skill-popup');
        skillPopup.dataset.sectionIndex = sectionIndex;
        skillPopup.style.display = 'block';

        // Display current skills for the section
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

    function selectSkill(skillElement) {
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

        // Update the hidden input
        const hiddenProcessesInput = document.getElementById(`projects[${sectionIndex}].processes`);
        if (hiddenProcessesInput) {
            hiddenProcessesInput.value = selectedProcesses[sectionIndex].join(',');
        }
    }

    function applySelectedSkills() {
        const skillPopup = document.getElementById('skill-popup');
        const sectionIndex = skillPopup.dataset.sectionIndex;
        const selectedSkillsContainer = document.getElementById(`selected-skills-${sectionIndex}`);
        const hiddenSkillsInput = document.getElementById(`projects[${sectionIndex}].skills`);

        if (!hiddenSkillsInput) {
            console.error(`Hidden skills input not found for section ${sectionIndex}`);
            closeSkillPopup();
            return;
        }

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
        const experienceContainer = document.getElementById('experience-sections');
        const newSection = document.querySelector('.experience').cloneNode(true);

        // Update the current index
        const currentIndex = sectionCount;
        sectionCount++;

        // Update input fields
        const inputs = newSection.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            let name = input.getAttribute('name');
            let id = input.getAttribute('id');
            if (name) {
                name = name.replace(/\[\d+\]/g, `[${currentIndex}]`);
                input.setAttribute('name', name);
                // Reset the value
                if (input.type === 'checkbox' || input.type === 'radio') {
                    input.checked = false;
                } else {
                    input.value = '';
                }
            }
            if (id) {
                id = id.replace(/\[\d+\]/g, `[${currentIndex}]`);
                input.setAttribute('id', id);
            }
        });

        // Update selected skills container
        const selectedSkillsContainer = newSection.querySelector('.skill-display');
        selectedSkillsContainer.id = `selected-skills-${currentIndex}`;
        selectedSkillsContainer.innerHTML = '';

        // Update hidden skills input
        const hiddenSkillsInput = newSection.querySelector('input[type="hidden"][name$=".skills"]');
        if (hiddenSkillsInput) {
            hiddenSkillsInput.id = `projects[${currentIndex}].skills`;
            hiddenSkillsInput.value = '';
        }

        // Update hidden processes input
        const hiddenProcessesInput = newSection.querySelector('input[type="hidden"][name$=".processes"]');
        if (hiddenProcessesInput) {
            hiddenProcessesInput.id = `projects[${currentIndex}].processes`;
            hiddenProcessesInput.value = '';
        }

        // Update process chips container id
        const processChipsContainer = newSection.querySelector('.process-chips');
        if (processChipsContainer) {
            processChipsContainer.id = `process-chips-${currentIndex}`;
        }

        // Update process chips
        const processChips = newSection.querySelectorAll('.process-chip');
        processChips.forEach(chip => {
            chip.classList.remove('selected');
            chip.onclick = function () {
                selectProcess(chip, currentIndex);
            };
        });

        // Update skill add button
        const skillAddButton = newSection.querySelector('.skill-add-button');
        skillAddButton.onclick = function () {
            openSkillPopup(currentIndex);
        };

        // Clear responsibility textarea
        const responsibilityTextarea = newSection.querySelector('textarea[name$=".responsibility"]');
        if (responsibilityTextarea) {
            responsibilityTextarea.value = '';
        }

        // Append the new section
        experienceContainer.appendChild(newSection);
    }

    // Initial event listeners
    document.querySelectorAll('.skill-add-button').forEach((button, index) => {
        button.onclick = function () {
            openSkillPopup(index);
        };
    });

    document.querySelectorAll('.process-chip').forEach(chip => {
        const processChipsContainer = chip.closest('.process-chips');
        const sectionIndex = processChipsContainer.id.split('-').pop();
        chip.onclick = function () {
            selectProcess(chip, sectionIndex);
        };
    });

    document.querySelectorAll('#skill-popup .skill-chip').forEach(chip => {
        chip.onclick = function () {
            selectSkill(chip);
        };
    });

    // Popup apply button
    document.querySelector('.apply-button').addEventListener('click', applySelectedSkills);

    // "Add Experience" button
    document.querySelector('.history-add-button').addEventListener('click', addExperienceSection);

    // Expose functions to global scope if needed
    window.openSkillPopup = openSkillPopup;
    window.closeSkillPopup = closeSkillPopup;
    window.selectSkill = selectSkill;
    window.selectProcess = selectProcess;
    window.applySelectedSkills = applySelectedSkills;
    window.addExperienceSection = addExperienceSection;
});
