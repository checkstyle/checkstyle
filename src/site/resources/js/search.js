(function () {
    "use strict";

    var MIN_QUERY_LENGTH = 2;
    var MAX_RESULTS      = 20;
    var focusedResultIndex = -1;
    var resultsContainer   = null;
    var searchInput        = null;
    var TYPE_COLORS = {
        "Check":       "#c00",
        "Filter":      "#2e7d32",
        "File Filter": "#1565c0",
        "General":     "#555"
    };

    var TYPE_LABELS = {
        "Check":       "Check",
        "Filter":      "Filter",
        "File Filter": "File Filter",
        "General":     "Doc"
    };
    var debounceTimer = null;

    /**
     * Injects the search widget HTML into the sidebar and initializes event listeners.
     */
    function injectSearchWidget() {
        var sidebar = document.querySelector("nav.sidebar-nav");
        if (!sidebar) { return; }
        var wrapper = document.createElement("div");
        wrapper.id = "checkstyle-search-wrapper";

        var posWrap = document.createElement("div");
        posWrap.style.position = "relative";

        searchInput = document.createElement("input");
        searchInput.type         = "search";
        searchInput.id           = "checkstyle-search-input";
        searchInput.placeholder  = "Search docs\u2026";
        searchInput.autocomplete = "off";
        searchInput.setAttribute("aria-label",       "Search documentation");
        searchInput.setAttribute("aria-autocomplete","list");
        searchInput.setAttribute("aria-controls",    "checkstyle-search-results");
        searchInput.setAttribute("aria-expanded",    "false");
        searchInput.setAttribute("role",             "combobox");

        resultsContainer = document.createElement("div");
        resultsContainer.id = "checkstyle-search-results";
        resultsContainer.setAttribute("role",       "listbox");
        resultsContainer.setAttribute("aria-label", "Search results");
        resultsContainer.style.display = "none";

        posWrap.appendChild(searchInput);
        posWrap.appendChild(resultsContainer);
        wrapper.appendChild(posWrap);

        sidebar.insertBefore(wrapper, sidebar.firstChild);

        searchInput.addEventListener("input",   onInput);
        searchInput.addEventListener("keydown", onKeyDown);
        searchInput.addEventListener("focus",   onFocus);

        document.addEventListener("click", function (e) {
            if (!wrapper.contains(e.target)) { hideResults(); }
        });

        document.addEventListener("keydown", function (e) {
            var tag = document.activeElement ? document.activeElement.tagName : "";
            if (e.key === "s" && tag !== "INPUT" && tag !== "TEXTAREA"
                    && !e.ctrlKey && !e.metaKey && !e.altKey) {
                e.preventDefault();
                searchInput.focus();
                searchInput.select();
            }
        });
    }

    /**
     * Filters and scores the search index entries based on the query.
     *
     * @param {string} query The search string provided by the user.
     * @param {Array} entries The full search index array.
     * @returns {Array} A sorted array of the top matching entries.
     */
    function search(query, entries) {
        var q = query.toLowerCase().trim();
        if (q.length < MIN_QUERY_LENGTH) {
            return [];
        }
        var tokens = q.split(/\s+/).filter(Boolean);
        var scored = [];

        for (var i = 0; i < entries.length; i++) {
            var entry    = entries[i];
            var title    = (entry.title       || "").toLowerCase();
            var desc     = (entry.description || "").toLowerCase();
            var keywords = (entry.keywords    || "").toLowerCase();
            var score    = 0;

            if (title === q)                     { score = 100; }
            else if (title.indexOf(q) === 0)     { score = 90;  }
            else if (title.indexOf(q) !== -1)    { score = 70;  }
            else if (desc.indexOf(q) !== -1)     { score = 50;  }
            else if (keywords.indexOf(q) !== -1) { score = 20;  }

            if (score === 0 && tokens.length > 1) {
                var allMatch = tokens.every(function (t) {
                    return title.indexOf(t) !== -1 || keywords.indexOf(t) !== -1;
                });
                if (allMatch) { score = 35; }
            }
            if (score === 0 && q.length >= 3 && fuzzyMatch(q, title)) {
                score = 10;
            }

            if (score > 0) {
                scored.push({ entry: entry, score: score });
            }
        }
        scored.sort(function (a, b) {
            if (b.score !== a.score) { return b.score - a.score; }
            return (a.entry.title || "").localeCompare(b.entry.title || "");
        });
        return scored.slice(0, MAX_RESULTS).map(function (s) { return s.entry; });
    }

    /**
     * Performs a subsequence fuzzy match to catch minor typos.
     * @param {string} query The lowercase query string.
     * @param {string} target The lowercase target text (usually the title).
     * @returns {boolean} True if all characters in the query appear in order within the target.
     */
    function fuzzyMatch(query, target) {
        var qi = 0;
        for (var ti = 0; ti < target.length && qi < query.length; ti++) {
            if (query[qi] === target[ti]) { qi++; }
        }
        return qi === query.length;
    }

    /**
     * Clears the current results and renders the new set of grouped search results.
     * @param {Array} results Array of matching entry objects.
     * @param {string} query The original query string used for highlighting.
     */
    function renderResults(results, query) {
        resultsContainer.innerHTML = "";
        focusedResultIndex = -1;

        if (results.length === 0) {
            var msg = document.createElement("div");
            msg.className   = "cs-search-no-results";
            msg.textContent = "No results for \u201c" + query + "\u201d";
            resultsContainer.appendChild(msg);
            showResults();
            return;
        }
        var categories = [];
        var grouped    = {};
        results.forEach(function (entry) {
            var cat = entry.category || "General";
            if (!grouped[cat]) {
                grouped[cat] = [];
                categories.push(cat);
            }
            grouped[cat].push(entry);
        });

        categories.forEach(function (cat) {
            var catEl = document.createElement("div");
            catEl.className   = "cs-search-category";
            catEl.textContent = cat;
            resultsContainer.appendChild(catEl);
            grouped[cat].forEach(function (entry) {
                resultsContainer.appendChild(createResultItem(entry, query));
            });
        });
        showResults();
    }

    /**
     * Constructs the DOM element for a single search result item.
     * @param {Object} entry The search index entry data.
     * @param {string} query The query string for match highlighting.
     * @returns {HTMLElement} The formatted anchor element for the result.
     */
    function createResultItem(entry, query) {
        var color = TYPE_COLORS[entry.type] || TYPE_COLORS["General"];
        var label = TYPE_LABELS[entry.type] || "Doc";

        var item = document.createElement("a");
        item.className            = "cs-search-result";
        item.href                 = entry.url;
        item.style.borderLeftColor = color;
        item.setAttribute("role",     "option");
        item.setAttribute("tabindex", "-1");

        var titleRow = document.createElement("div");
        titleRow.className = "cs-search-title-row";

        var titleEl = document.createElement("span");
        titleEl.className = "cs-search-result-title";
        titleEl.innerHTML = highlight(entry.title || "", query);

        var badge = document.createElement("span");
        badge.className        = "cs-search-type-badge";
        badge.textContent      = label;
        badge.style.background = color + "18";
        badge.style.color      = color;
        badge.style.borderColor = color + "44";

        titleRow.appendChild(titleEl);
        titleRow.appendChild(badge);
        item.appendChild(titleRow);

        if (entry.description) {
            var descEl = document.createElement("div");
            descEl.className   = "cs-search-result-desc";
            descEl.textContent = entry.description;
            item.appendChild(descEl);
        }

        item.addEventListener("click", function (e) {
            e.preventDefault();
            hideResults();
            window.location.href = item.href;
        });

        item.addEventListener("keydown", function (e) {
            var items = resultsContainer.querySelectorAll(".cs-search-result");
            if (e.key === "ArrowDown") {
                e.preventDefault();
                if (focusedResultIndex < items.length - 1) {
                    focusedResultIndex++;
                    items[focusedResultIndex].focus();
                }
            } else if (e.key === "ArrowUp") {
                e.preventDefault();
                if (focusedResultIndex > 0) {
                    focusedResultIndex--;
                    items[focusedResultIndex].focus();
                } else {
                    focusedResultIndex = -1;
                    searchInput.focus();
                }
            } else if (e.key === "Escape") {
                hideResults();
                searchInput.focus();
            }
        });
        return item;
    }

    /**
     * Wraps the first occurrence of the query in the text with <mark> tags.
     * @param {string} text The original text.
     * @param {string} query The query string to highlight.
     * @returns {string} HTML string with highlighting and escaping applied.
     */
    function highlight(text, query) {
        if (!query) { return escapeHtml(text); }
        var idx = text.toLowerCase().indexOf(query.toLowerCase());
        if (idx === -1) { return escapeHtml(text); }
        return escapeHtml(text.substring(0, idx))
            + "<mark>" + escapeHtml(text.substring(idx, idx + query.length)) + "</mark>"
            + escapeHtml(text.substring(idx + query.length));
    }

    /**
     * Escapes special HTML characters to prevent XSS.
     * @param {string} str The string to be escaped.
     * @returns {string} The safely escaped string.
     */
    function escapeHtml(str) {
        return (str || "")
            .replace(/&/g,  "&amp;")
            .replace(/</g,  "&lt;")
            .replace(/>/g,  "&gt;")
            .replace(/"/g,  "&quot;");
    }

    /**
     * Displays the search results dropdown.
     */
    function showResults() {
        resultsContainer.style.display = "block";
        searchInput.setAttribute("aria-expanded", "true");
    }

    /**
     * Hides the search results dropdown and resets the focus index.
     */
    function hideResults() {
        resultsContainer.style.display = "none";
        searchInput.setAttribute("aria-expanded", "false");
        focusedResultIndex = -1;
    }

    /**
     * Handles the input event with debouncing to limit search frequency.
     */
    function onInput() {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(function() {
            var query = searchInput.value.trim();
            if (query.length < MIN_QUERY_LENGTH) {
                hideResults();
                return;
            }
            var entries = window.CHECKSTYLE_SEARCH_INDEX;
            if (!entries || entries.length === 0) {
                return;
            }
            renderResults(search(query, entries), query);
        }, 150);
    }

    /**
     * Shows existing results when the search input gains focus.
     */
    function onFocus() {
        if (searchInput.value.trim().length >= MIN_QUERY_LENGTH
                && resultsContainer.innerHTML !== "") {
            showResults();
        }
    }

    /**
     * Handles keyboard shortcuts (Escape and ArrowDown) specifically for the search input.
     */
    function onKeyDown(e) {
        var items = resultsContainer.querySelectorAll(".cs-search-result");
        if (e.key === "Escape") {
            hideResults();
            searchInput.blur();
            return;
        }
        if (e.key === "ArrowDown") {
            e.preventDefault();
            if (items.length > 0) {
                focusedResultIndex = 0;
                items[0].focus();
            }
        }
    }

    document.addEventListener("DOMContentLoaded", function () {
        injectSearchWidget();
    });

}());
