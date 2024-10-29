let projectIndex = 0; // プロジェクトのインデックスを管理

    document.getElementById('add-project-btn').addEventListener('click', function() {
        // プロジェクトフォームの要素を作成
        const newProjectForm = document.createElement('div');
        newProjectForm.classList.add('project-form');
        newProjectForm.innerHTML = `
            <input type="hidden" name="projects[${projectIndex}].projectId" value="0" />  <!-- 新しいプロジェクトID -->
            <p>プロジェクト名 </p>
            <input type="text" name="projects[${projectIndex}].projectName" placeholder="" />
            <p>作業場所 </p>
            <input type="text" name="projects[${projectIndex}].location" placeholder="" />
            <p>開始日 </p>
            <input type="date" name="projects[${projectIndex}].startDate" />
            <p>終了日 </p>
            <input type="date" name="projects[${projectIndex}].endDate" />
        `;

        // 新しいプロジェクトフォームをコンテナに追加
        document.getElementById('projects-container').appendChild(newProjectForm);

        projectIndex++; // インデックスをインクリメント
    });