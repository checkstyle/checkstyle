document.addEventListener("DOMContentLoaded", function () {
    var topBar = document.createElement("div");
    topBar.id = "doc-search-topbar";

    var input = document.createElement("input");
    input.type = "text";
    input.id = "doc-search-input";
    input.placeholder = "Search documentation...";
    input.setAttribute("autocomplete", "off");

    var results = document.createElement("ul");
    results.id = "doc-search-results";
    results.className = "doc-search-hidden";

    topBar.appendChild(input);
    topBar.appendChild(results);

    document.body.insertBefore(topBar, document.body.firstChild);

    var allLinks = Array.from
    (document.querySelectorAll(".nav-list a, .well.sidebar-nav a, .accordion a"));

    var searchableItems = allLinks
        .map(function (link) {
            return {
                title: link.textContent.trim(),
                href: link.href
            };
        })
        .filter(function (item) {
            return item.title.length > 0;
        });

    function normalize(text) {
        return text.toLowerCase().trim();
    }

    function getMatches(query) {
        var normalizedQuery = normalize(query);

        var startsWithMatches = searchableItems.filter(function (item) {
            return normalize(item.title).startsWith(normalizedQuery);
        });

        var containsMatches = searchableItems.filter(function (item) {
            var title = normalize(item.title);
            return !title.startsWith(normalizedQuery) && title.includes(normalizedQuery);
        });

        return startsWithMatches.concat(containsMatches).slice(0, 8);
    }

    function renderResults(matches, query) {
        results.innerHTML = "";

        if (query.length < 1) {
            results.classList.add("doc-search-hidden");
            return;
        }

        if (matches.length === 0) {
            var noResult = document.createElement("li");
            noResult.className = "doc-search-result-item";
            noResult.textContent = 'No suggestions found';
            results.appendChild(noResult);
            results.classList.remove("doc-search-hidden");
            return;
        }

        matches.forEach(function (item) {
            var li = document.createElement("li");
            li.className = "doc-search-result-item";
            li.textContent = item.title;

            li.addEventListener("click", function () {
                window.location.href = item.href;
            });

            results.appendChild(li);
        });

        results.classList.remove("doc-search-hidden");
    }

    input.addEventListener("input", function () {
        var query = input.value;
        var matches = getMatches(query);
        renderResults(matches, query);
    });

    input.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
            var query = input.value;
            var matches = getMatches(query);

            if (matches.length > 0) {
                window.location.href = matches[0].href;
            }
        }
    });

    document.addEventListener("click", function (event) {
        if (!topBar.contains(event.target)) {
            results.classList.add("doc-search-hidden");
        }
    });
});
