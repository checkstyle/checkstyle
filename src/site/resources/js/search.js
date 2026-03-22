(function () {
    'use strict';

    var BASE_URL = (function () {
        var scripts = document.querySelectorAll('script[src]');
        for (var i = 0; i < scripts.length; i++) {
            var m = scripts[i].src.match(/(.*\/)js\/[^/]*$/);
            if (m) return m[1];
        }
        return './';
    })();

    var index = null;
    var loadPromise = null;

    function loadIndex() {
        if (loadPromise) return loadPromise;
        loadPromise = fetch(BASE_URL + 'search-index.json')
            .then(function (r) { return r.json(); })
            .then(function (data) { index = data; })
            .catch(function (err) {
                console.warn('Search index unavailable:', err);
            });
        return loadPromise;
    }

    function scrollToHighlight() {
        var params = new URLSearchParams(window.location.search);
        var query = params.get('highlight');
        if (!query) return;

        var needle = query.toLowerCase().split(/\s+/).filter(Boolean)[0];
        if (!needle) return;

        var content = document.querySelector('#bodyColumn')
            || document.querySelector('main')
            || document.body;

        var walker = document.createTreeWalker(
            content,
            NodeFilter.SHOW_TEXT,
            {
                acceptNode: function (node) {
                    var tag = node.parentElement && node.parentElement.tagName.toLowerCase();
                    if (tag === 'script' || tag === 'style') {
                        return NodeFilter.FILTER_REJECT;
                    }
                    return node.textContent.toLowerCase().includes(needle)
                        ? NodeFilter.FILTER_ACCEPT
                        : NodeFilter.FILTER_SKIP;
                }
            }
        );

        var targetNode = walker.nextNode();
        if (!targetNode) return;

        var parent = targetNode.parentNode;
        var text = targetNode.textContent;
        var idx = text.toLowerCase().indexOf(needle);
        if (idx === -1) return;

        var before = document.createTextNode(text.substring(0, idx));
        var mark = document.createElement('mark');
        mark.className = 'cs-highlight-jump';
        mark.textContent = text.substring(idx, idx + needle.length);
        var after = document.createTextNode(text.substring(idx + needle.length));

        parent.replaceChild(after, targetNode);
        parent.insertBefore(mark, after);
        parent.insertBefore(before, mark);

        requestAnimationFrame(function () {
            requestAnimationFrame(function () {
                mark.scrollIntoView({ behavior: 'smooth', block: 'center' });
            });
        });

        setTimeout(function () {
            mark.style.transition = 'background 0.8s, color 0.8s';
            mark.style.background = 'transparent';
            mark.style.color = 'inherit';
            setTimeout(function () {
                var restored = document.createTextNode(text);
                if (mark.parentNode) {
                    mark.parentNode.insertBefore(restored, mark);
                    if (before.parentNode) before.parentNode.removeChild(before);
                    mark.parentNode.removeChild(mark);
                    if (after.parentNode) after.parentNode.removeChild(after);
                }
            }, 900);
        }, 3000);
    }

    var GENERIC_SECTIONS = [
        'description', 'properties', 'examples', 'package',
        'violation messages', 'notes', 'since', 'error messages'
    ];

    function scoreEntry(entry, terms, queryLow) {
        var titleLow = (entry.title || '').toLowerCase();
        var sectionLow = (entry.section || '').toLowerCase();
        var textLow = (entry.text || '').toLowerCase();

        var score = 0;
        var allMatch = true;

        if (titleLow === queryLow) score += 100;
        else if (titleLow.startsWith(queryLow)) score += 50;

        if (sectionLow === queryLow) score += 60;
        else if (sectionLow.startsWith(queryLow)) score += 30;

        for (var j = 0; j < terms.length; j++) {
            var t = terms[j];
            var inTitle = titleLow.includes(t);
            var inSection = sectionLow.includes(t);
            var inText = textLow.includes(t);

            if (!inTitle && !inSection && !inText) { allMatch = false; break; }
            if (inTitle) score += 20;
            if (inSection) score += 10;
            if (inText) {
                score += 1;
                var count = 0, pos = 0;
                while ((pos = textLow.indexOf(t, pos)) !== -1) { count++; pos += t.length; }
                score += Math.min(5, Math.floor(Math.log2(count + 1)));
                var firstPos = textLow.indexOf(t);
                if (textLow.length > 0 && firstPos / textLow.length < 0.2) score += 3;
            }
        }

        if (!allMatch) return -1;

        if (sectionLow !== titleLow && GENERIC_SECTIONS.indexOf(sectionLow) !== -1) {
            score -= 5;
        }

        return score;
    }

    function search(query) {
        if (!index || !query.trim()) return [];

        var queryLow = query.toLowerCase().trim();
        var terms = queryLow.split(/\s+/).filter(Boolean);

        var scored = [];
        for (var i = 0; i < index.length; i++) {
            var s = scoreEntry(index[i], terms, queryLow);
            if (s > 0) scored.push(Object.assign({}, index[i], { score: s }));
        }

        var best = {};
        for (var k = 0; k < scored.length; k++) {
            var baseUrl = scored[k].url.split('#')[0];
            if (!best[baseUrl] || scored[k].score > best[baseUrl].score) {
                best[baseUrl] = scored[k];
            }
        }

        var results = [];
        for (var key in best) {
            if (Object.prototype.hasOwnProperty.call(best, key)) results.push(best[key]);
        }
        results.sort(function (a, b) { return b.score - a.score; });
        return results.slice(0, 15);
    }

    function buildHref(item, queryLow) {
        var base = BASE_URL + item.url;
        if (item.url.includes('#')) return base;
        var sep = base.includes('?') ? '&' : '?';
        return base + sep + 'highlight=' + encodeURIComponent(queryLow);
    }

    function escHtml(s) {
        return String(s)
            .replace(/&/g, '&amp;').replace(/</g, '&lt;')
            .replace(/>/g, '&gt;').replace(/"/g, '&quot;');
    }

    function highlight(text, query) {
        var result = escHtml(text);
        var terms = query.trim().split(/\s+/).filter(Boolean);
        for (var i = 0; i < terms.length; i++) {
            var re = new RegExp(
                '(' + terms[i].replace(/[.*+?^${}()|[\]\\]/g, '\\$&') + ')',
                'gi'
            );
            result = result.replace(re, '<mark>$1</mark>');
        }
        return result;
    }

    function truncate(text, displayLen, queryLow) {
        if (!text) return '';
        if (text.length <= displayLen) return text;
        var needle = (queryLow || '').split(/\s+/).filter(Boolean)[0] || '';
        var anchor = 0;
        if (needle) {
            var idx = text.toLowerCase().indexOf(needle);
            if (idx !== -1) anchor = Math.max(0, idx - 30);
        }
        return (anchor > 0 ? '\u2026' : '') +
            text.substring(anchor, anchor + displayLen) + '\u2026';
    }

    function injectSearchBar() {
        var target = document.querySelector('#leftColumn nav.sidebar-nav')
            || document.querySelector('nav.sidebar-nav')
            || document.querySelector('#leftColumn nav')
            || document.querySelector('#leftColumn');
        if (!target) return;

        var wrapper = document.createElement('div');
        wrapper.id = 'cs-search-wrapper';
        wrapper.setAttribute('role', 'search');
        wrapper.innerHTML =
            '<div id="cs-search-box">' +
            '<input id="cs-search-input" type="search" ' +
            'placeholder="Search docs\u2026" autocomplete="off" ' +
            'aria-label="Search documentation" ' +
            'aria-expanded="false" aria-haspopup="listbox" />' +
            '<span id="cs-search-spinner" aria-hidden="true"></span>' +
            '</div>' +
            '<div id="cs-search-results" role="listbox" ' +
            'aria-label="Search results" hidden="hidden"></div>';

        target.insertBefore(wrapper, target.firstChild);

        var input = document.getElementById('cs-search-input');
        var results = document.getElementById('cs-search-results');
        var spinner = document.getElementById('cs-search-spinner');
        var timer;

        input.addEventListener('focus', function () {
            spinner.classList.add('visible');
            loadIndex().then(function () { spinner.classList.remove('visible'); });
        });

        input.addEventListener('input', function () {
            clearTimeout(timer);
            timer = setTimeout(function () {
                var q = input.value.trim();
                if (!q) { closeResults(); return; }
                if (!index) {
                    spinner.classList.add('visible');
                    loadIndex().then(function () {
                        spinner.classList.remove('visible');
                        renderResults(q);
                    });
                } else {
                    renderResults(q);
                }
            }, 150);
        });

        input.addEventListener('keydown', function (e) {
            if (e.key === 'Escape') { closeResults(); input.blur(); }
            if (e.key === 'ArrowDown') {
                e.preventDefault();
                var first = results.querySelector('[role="option"]');
                if (first) first.focus();
            }
        });

        document.addEventListener('click', function (e) {
            if (!wrapper.contains(e.target)) closeResults();
        });

        function renderResults(q) {
            var hits = search(q);
            var queryLow = q.toLowerCase().trim();

            if (!hits.length) {
                results.innerHTML =
                    '<div class="cs-no-results">No results for <strong>' +
                    escHtml(q) + '</strong></div>';
                results.removeAttribute('hidden');
                input.setAttribute('aria-expanded', 'true');
                return;
            }

            var groups = {}, order = [];
            for (var i = 0; i < hits.length; i++) {
                var cat = hits[i].category;
                if (!groups[cat]) { groups[cat] = []; order.push(cat); }
                groups[cat].push(hits[i]);
            }

            var html = '';
            for (var c = 0; c < order.length; c++) {
                var cat = order[c];
                var items = groups[cat];
                html += '<div class="cs-group-label">' + escHtml(cat) + '</div>';

                for (var k = 0; k < items.length; k++) {
                    var item = items[k];
                    var isPageLevel = item.section === item.title;
                    var snippet = highlight(truncate(item.text, 120, queryLow), q);
                    var href = buildHref(item, queryLow);

                    html +=
                        '<a class="cs-result-item" href="' + escHtml(href) +
                        '" role="option" tabindex="-1">' +
                        '<span class="cs-result-title">' +
                        highlight(item.title, q) +
                        (!isPageLevel
                            ? ' <span class="cs-result-sep">\u203a</span> ' +
                            highlight(item.section, q)
                            : '') +
                        '</span>' +
                        (snippet
                            ? '<span class="cs-result-snippet">' + snippet + '</span>'
                            : '') +
                        '</a>';
                }
            }

            results.innerHTML = html;
            results.removeAttribute('hidden');
            input.setAttribute('aria-expanded', 'true');

            var links = results.querySelectorAll('.cs-result-item');
            for (var i = 0; i < links.length; i++) {
                (function (el, idx, all) {
                    el.addEventListener('keydown', function (ev) {
                        if (ev.key === 'ArrowDown') {
                            ev.preventDefault();
                            if (all[idx + 1]) all[idx + 1].focus();
                        }
                        if (ev.key === 'ArrowUp') {
                            ev.preventDefault();
                            if (idx === 0) input.focus();
                            else all[idx - 1].focus();
                        }
                        if (ev.key === 'Escape') { closeResults(); input.focus(); }
                    });
                })(links[i], i, links);
            }
        }

        function closeResults() {
            results.setAttribute('hidden', 'hidden');
            results.innerHTML = '';
            input.setAttribute('aria-expanded', 'false');
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', function () {
            injectSearchBar();
            scrollToHighlight();
        });
    } else {
        injectSearchBar();
        scrollToHighlight();
    }

})();
