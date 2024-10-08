document.addEventListener('DOMContentLoaded', function () {
    let selectedSkills = [];
    let selectedProcesses = [];

    function openSkillPopup() {
        document.getElementById('skill-popup').style.display = 'block';
    }

    function closeSkillPopup() {
        document.getElementById('skill-popup').style.display = 'none';
    }

    function selectSkill(skillElement, category) {
        const skillName = skillElement.textContent;
        const skillKey = `${category}-${skillName}`;

        if (!selectedSkills.includes(skillKey)) {
            selectedSkills.push(skillKey);
            skillElement.classList.add('selected');
        } else {
            selectedSkills = selectedSkills.filter(skill => skill !== skillKey);
            skillElement.classList.remove('selected');
        }
    }

    function selectProcess(processElement) {
        const processName = processElement.textContent;

        if (!selectedProcesses.includes(processName)) {
            selectedProcesses.push(processName);
            processElement.classList.add('selected');
        } else {
            selectedProcesses = selectedProcesses.filter(process => process !== processName);
            processElement.classList.remove('selected');
        }
    }

    function applySelectedSkills() {
        const selectedSkillsContainer = document.getElementById('selected-skills');
        selectedSkillsContainer.innerHTML = '';

        selectedSkills.forEach(skillKey => {
            const [category, skillName] = skillKey.split('-');
            const skillTag = document.createElement('span');
            skillTag.className = 'skill-chip selected';
            skillTag.textContent = skillName;
            selectedSkillsContainer.appendChild(skillTag);
        });

        closeSkillPopup();
    }

    // window object is used to make functions available in the global scope
    window.openSkillPopup = openSkillPopup;
    window.closeSkillPopup = closeSkillPopup;
    window.selectSkill = selectSkill;
    window.selectProcess = selectProcess;
    window.applySelectedSkills = applySelectedSkills;
});

function addExperienceSection() {
    const experienceContainer = document.getElementById('experience-sections');
    const experienceSection = document.querySelector('.experience').cloneNode(true);
    experienceContainer.appendChild(experienceSection);
}