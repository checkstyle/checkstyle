document.addEventListener("DOMContentLoaded", function () {
  // Create search UI dynamically
  const container = document.createElement("div");
  container.id = "search-container";

  container.innerHTML = `
    <input type="text" id="search-box" placeholder="Search Checkstyle docs..." />
    <div id="search-results"></div>
  `;

  // Add to page (top)
  document.body.prepend(container);

  const input = document.getElementById('search-box');
  const resultsBox = document.getElementById('search-results');

  let searchData = [];

  // Load JSON
  fetch('search-index.json')
    .then(res => res.json())
    .then(data => {
      searchData = data;
    });

  // Search logic
  input.addEventListener('input', function () {
    const query = this.value.toLowerCase();
    resultsBox.innerHTML = '';

    if (!query) return;

    const results = searchData.filter(item =>
      item.title.toLowerCase().includes(query) ||
      item.keywords.some(k => k.includes(query))
    );

    results.slice(0, 5).forEach(item => {
      const div = document.createElement('div');
      div.innerHTML = `<a href="${item.url}">${item.title}</a>`;
      resultsBox.appendChild(div);
    });
  });
});