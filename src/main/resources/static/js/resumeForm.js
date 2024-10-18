// src/main/resources/static/js/resumeForm.js

document.addEventListener('DOMContentLoaded', function () {
    let selectedSkills = {}; // { sectionIndex: { os: [], db: [], language: [], tool: [] } }
    let selectedProcesses = {}; // { sectionIndex: [processes] }
    let sectionCount = document.querySelectorAll('.experience').length; // Initialize based on existing sections

    // Initialize selectedSkills and selectedProcesses based on existing form data
    document.querySelectorAll('.experience').forEach((section, index) => {
        // Initialize selectedSkills for each category
        selectedSkills[index] = { os: [], db: [], language: [], tool: [] };

        ['os', 'db', 'language', 'tool'].forEach(category => {
            const skillsInput = section.querySelector(`input[name="projects[${index}].skills.${category}"]`);
            if (skillsInput && skillsInput.value) {
                selectedSkills[index][category] = skillsInput.value.split(',');
                selectedSkills[index][category].forEach(skill => {
                    const skillChip = section.querySelector(`.skill-chip:contains("${skill}")`);
                    if (skillChip) {
                        skillChip.classList.add('selected');
                    }
                });
            }
        });

        // Initialize selectedProcesses
        const processesInput = section.querySelector(`input[name="projects[${index}].processes"]`);
        if (processesInput && processesInput.value) {
            selectedProcesses[index] = processesInput.value.split(',');
            selectedProcesses[index].forEach(process => {
                const processChip = section.querySelector(`.process-chip:contains("${process}")`);
                if (processChip) {
                    processChip.classList.add('selected');
                }
            });
        }
    });

    function openSkillPopup(sectionIndex) {
        const skillPopup = document.getElementById('skill-popup');
        skillPopup.dataset.sectionIndex = sectionIndex;
        skillPopup.style.display = 'block';

        // Display current skills for the section
        const currentSkills = selectedSkills[sectionIndex] || { os: [], db: [], language: [], tool: [] };
        document.querySelectorAll('#skill-popup .skill-chip').forEach(chip => {
            const skillName = chip.textContent.trim();
            const category = chip.parentElement.getAttribute('data-category');
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

    function selectSkill(skillElement) {
        const skillName = skillElement.textContent.trim();
        const skillPopup = document.getElementById('skill-popup');
        const sectionIndex = skillPopup.dataset.sectionIndex;
        const category = skillElement.parentElement.getAttribute('data-category');

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

        // Update hidden inputs for each category
        ['os', 'db', 'language', 'tool'].forEach(category => {
            const hiddenSkillsInput = document.getElementById(`projects[${sectionIndex}].skills.${category}`);
            if (hiddenSkillsInput) {
                hiddenSkillsInput.value = selectedSkills[sectionIndex][category].join(',');
            }

            // Update the skill display
            const selectedSkillsContainer = document.querySelector(`#selected-skills-${sectionIndex}-${category}`);
            if (selectedSkillsContainer) {
                // Clear existing displayed skills
                selectedSkillsContainer.innerHTML = '';

                // Add selected skills from the category
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

        // Update the hidden input
        const hiddenProcessesInput = document.getElementById(`projects[${sectionIndex}].processes`);
        if (hiddenProcessesInput) {
            hiddenProcessesInput.value = selectedProcesses[sectionIndex].join(',');
        }
    }

    function addExperienceSection() {
        const experienceContainer = document.getElementById('experience-sections');
        const templateSection = document.querySelector('.experience');
        const newSection = templateSection.cloneNode(true);

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

        // Reset selected skills
        ['os', 'db', 'language', 'tool'].forEach(category => {
            const hiddenSkillsInput = newSection.querySelector(`input[type="hidden"][name="projects[${currentIndex}].skills.${category}"]`);
            if (hiddenSkillsInput) {
                hiddenSkillsInput.value = '';
            }
        });

        // Reset selected processes
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

        // Update skill display
        ['os', 'db', 'language', 'tool'].forEach(category => {
            const selectedSkillsContainer = newSection.querySelector(`#selected-skills-${currentIndex}-${category}`);
            if (selectedSkillsContainer) {
                selectedSkillsContainer.id = `selected-skills-${currentIndex}-${category}`;
                selectedSkillsContainer.innerHTML = '';
            }
        });

        // Update skill add button
        const skillAddButton = newSection.querySelector('.skill-add-button');
        if (skillAddButton) {
            skillAddButton.onclick = function () {
                openSkillPopup(currentIndex);
            };
        }

        // Clear responsibility textarea
        const responsibilityTextarea = newSection.querySelector('textarea[name$=".responsibility"]');
        if (responsibilityTextarea) {
            responsibilityTextarea.value = '';
        }

        // Append the new section
        experienceContainer.appendChild(newSection);

        // Initialize selectedSkills for the new section
        selectedSkills[currentIndex] = { os: [], db: [], language: [], tool: [] };
        selectedProcesses[currentIndex] = [];
    }

    // Initial event listeners for process chips
    document.querySelectorAll('.process-chip').forEach(chip => {
        const processChipsContainer = chip.closest('.process-chips');
        const sectionIndex = processChipsContainer.id.split('-').pop();
        chip.onclick = function () {
            selectProcess(chip, sectionIndex);
        };
    });

    // Initial event listeners for skill chips in popup
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
