document.addEventListener("DOMContentLoaded", () => {
    const tableBody = document.getElementById("accountTableBody");
    const sortButtons = document.querySelectorAll(".sort-arrow");

    sortButtons.forEach(button => {
        button.addEventListener("click", () => {
            const sortKey = button.dataset.sort; 
            const sortOrder = button.dataset.order; 
            sortTable(sortKey, sortOrder);
        });
    });

    function sortTable(sortKey, sortOrder) {
        const rows = Array.from(tableBody.querySelectorAll("tr"));

        rows.sort((a, b) => {
            const aValue = a.querySelector(`[data-${sortKey}]`).dataset[sortKey].toLowerCase();
            const bValue = b.querySelector(`[data-${sortKey}]`).dataset[sortKey].toLowerCase();

            if (sortOrder === "asc") {
                return aValue > bValue ? 1 : -1;
            } else {
                return aValue < bValue ? 1 : -1;
            }
        });

        rows.forEach(row => tableBody.appendChild(row));
    }
});
