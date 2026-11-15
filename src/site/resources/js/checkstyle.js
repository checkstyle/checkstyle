/*jslint browser: true */
/*jslint es6: true */
/*jshint esversion: 6 */
/*global window */
let scrollButton;
const scrollDistanceToButtonVisibility = 500;

window.addEventListener("load", function () {
    "use strict";
    scrollButton = document.querySelector(".pull-right > a");
    scrollButton.innerText = "To Top";
    scrollButton.style.display = "none";

    scrollButton.addEventListener("click", function (event) {
        event.preventDefault();
        document.documentElement.scrollTop = 0;
    });
});

// Set when the archive redirect below has already chosen a destination for the
// current URL. The filename redirect runs on "load", after this handler, and
// would otherwise replace the archive with the release-notes index.
let releaseNotesArchiveRequested = false;

document.addEventListener("DOMContentLoaded", () => {
    const url = new URL(window.location.href);
    if (!url.pathname.endsWith("/releasenotes.html")
            || !url.hash.startsWith("#Release_")
            || document.getElementById(url.hash.replace("#", ""))) {
        return;
    }

    const version = url.hash.split('_')[1];
    const versionParts = version.split(".");
    if (versionParts.length < 2) {
        return;
    }

    let major = parseInt(versionParts[0], 10);
    let minor = parseInt(versionParts[1], 10);

    if (major >= 1 && major < 6) {
        releaseNotesArchiveRequested = true;
        window.location.replace(`./release-notes-old-1-0-5-9.html${url.hash}`);
    }
    else if (major === 6 || major === 7) {
        releaseNotesArchiveRequested = true;
        window.location.replace(`./release-notes-old-6-0-7-8.html${url.hash}`);
    }
    else if (major === 8 && minor >= 0 && minor <= 34) {
        releaseNotesArchiveRequested = true;
        window.location.replace(`./release-notes-old-8-0-8-34.html${url.hash}`);
    }
    else if (major >= 8 && major <= 10) {
        releaseNotesArchiveRequested = true;
        window.location.replace(`./release-notes-old-8-35-10-26.html${url.hash}`);
    }
});

window.addEventListener("load", function () {
    const currentUrl = window.location.href;

    if (currentUrl.endsWith("/checks/") || currentUrl.endsWith("/checks/index.html")) {
        window.location.replace("../checks.html");
    }
    else if (document.title.startsWith("Redirecting to checks")) {
        const urlObj = new URL(currentUrl);
        const pathSegments = urlObj.pathname.split("/");
        const configHtmlFile = pathSegments[pathSegments.length - 1];
        const checkType = /config_([a-z]+).html/.exec(configHtmlFile)[1];
        const checkName = urlObj.hash.substring(1).toLowerCase();

        if (checkName) {
            if (checkType !== "filters" && checkType !== "filefilters") {
                window.location.replace(`./checks/${checkType}/${checkName}.html`);
            }
            else {
                window.location.replace(`./${checkType}/${checkName}.html`);
            }
        }
        else {
            if (checkType !== "filters" && checkType !== "filefilters") {
                window.location.replace(`./checks/${checkType}`);
            }
            else {
                window.location.replace(`./${checkType}`);
            }
        }
    }
    else if (document.title.startsWith("Redirecting")) {
        const urlObj = new URL(currentUrl);
        const pathSegments = urlObj.pathname.split("/");
        const oldHtmlFile = pathSegments[pathSegments.length - 1];

        const redirectMap = {
            "google_style.html": "google-style.html",
            "openjdk_style.html": "openjdk-style.html",
            "sun_style.html": "sun-style.html",
            "property_types.html": "property-types.html",
            "releasenotes.html": "release-notes.html",
            "report_issue.html": "report-issue.html",
            "result_reports.html": "result-reports.html",
            "style_configs.html": "style-configs.html",
            "writingchecks.html": "writing-checks.html",
            "writingfilefilters.html": "writing-filefilters.html",
            "writingfilters.html": "writing-filters.html",
            "writingjavadocchecks.html": "writing-javadoc-checks.html",
            "writinglisteners.html": "writing-listeners.html",
            "anttask.html": "ant-task.html",
            "beginning_development.html": "beginning-development.html",
            "doc_comments_style.html": "doc-comments-style.html"
        };

        if (redirectMap[oldHtmlFile]) {
            if (oldHtmlFile === "releasenotes.html" && releaseNotesArchiveRequested) {
                return;
            }

            const newUrl = `./${redirectMap[oldHtmlFile]}${urlObj.hash}`;

            const section = document.querySelector("section[name='Redirecting'] p");
            if (section) {
                section.innerHTML = `Redirecting to <a href="${newUrl}">${redirectMap[oldHtmlFile]}</a>`;
            }

            window.location.replace(newUrl);
        }
    }
});

// for newer version of site.
window.addEventListener("load", function () {
    const externalLinks = document.querySelectorAll(".externalLink");
    externalLinks.forEach((link) => {
        link.setAttribute("target", "_blank");
    });
});

window.addEventListener("scroll", function () {
    "use strict";
    if (document.documentElement.scrollTop > scrollDistanceToButtonVisibility) {
        scrollButton.style.display = "block";
    } else {
        scrollButton.style.display = "none";
    }
});

function setBodyColumnMargin() {
    const leftColumn = document.getElementById("leftColumn");
    const bodyColumn = document.getElementById("bodyColumn");

    if (window.innerWidth > 1920) {
      leftColumn.className = "span2";
      bodyColumn.className = "span10";
    } else if (window.innerWidth > 1280) {
      leftColumn.className = "span3";
      bodyColumn.className = "span9";
    } else if  (window.innerWidth > 823) {
      leftColumn.className = "span4";
      bodyColumn.className = "span8";
    } else {
      bodyColumn.className = "span12";
    }

    if (window.innerWidth > 823 && document.querySelector("#hamburger")) {
        resetStyling();
    }

    if (window.innerWidth < 823 && !document.querySelector("#hamburger")) {
        setCollapsableMenuButton();
    }
}

function setCollapsableMenuButton() {
    const hamburger = document.createElement("li");
    hamburger.id = "hamburger";

    for (let i = 0; i < 3; i++) {
        const line = document.createElement("span");
        hamburger.appendChild(line);
    }

    const breadcrumb = document.querySelector(".breadcrumb");
    breadcrumb.appendChild(hamburger);

    hamburger.addEventListener("click", (e) => {
        e.preventDefault();

        const menu = document.querySelector("#leftColumn");
        const breadcrumbs = document.querySelector("#breadcrumbs");
        const body = document.querySelector("#bodyColumn");

        menu.style.top = `${breadcrumbs.offsetHeight + 10}px`;
        menu.style.height = `calc(100vh - ${breadcrumbs.offsetHeight + 10}px)`;

        if (hamburger.classList.contains("openMenu")) {
            hamburger.classList.remove("openMenu");
            menu.style.display = "none";

            body.style.removeProperty("position");

            // Restore scroll
            window.scrollTo({ top: Number(body.dataset.scrollTop), behavior: "auto" });
            delete body.dataset.scrollTop;
        } else {
            hamburger.classList.add("openMenu");
            menu.style.display = "block";

            // Preserve current scroll position
            body.dataset.scrollTop = String(window.scrollY);

            // Prevent scrolling
            body.style.position = "fixed";
        }
    });
}

function resetStyling() {
    const leftColumn = document.querySelector("#leftColumn");
    const body = document.querySelector("body");
    const bodyColumn = document.querySelector("#bodyColumn");
    const hamburger = document.querySelector("#hamburger");
    const xright = document.querySelector(".xright");

    if (leftColumn) {
        leftColumn.removeAttribute("style");
    }
    if (body) {
        body.removeAttribute("style");
    }
    if (bodyColumn) {
        bodyColumn.style.removeProperty("position");
    }
    if (hamburger) {
        hamburger.remove();
    }
    if (xright && xright.lastChild) {
        xright.lastChild.remove();
    }
}

window.addEventListener("load", function () {
    document.querySelectorAll("pre code").forEach(trimCodeBlock);
});

function trimCodeBlock(codeBlock) {
    const textNodes = codeBlock.children;
    if (textNodes.length > 0) {
        const firstNodeIdx = 0;
        const lastNodeIdx = textNodes.length - 1;
        textNodes[firstNodeIdx].textContent = textNodes[firstNodeIdx].textContent.trimStart();
        textNodes[lastNodeIdx].textContent = textNodes[lastNodeIdx].textContent.trimEnd();
    }
}

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", setBodyColumnMargin);
}
else {
    setBodyColumnMargin();
}

window.addEventListener("resize", setBodyColumnMargin);

window.addEventListener("load", function () {
    "use strict";

    const tocPanel = document.querySelector(".toc-panel");
    const breadcrumbs = document.getElementById("breadcrumbs");

    if (!tocPanel || !breadcrumbs) {
        return;
    }

    function updateTocPosition() {
        const rect = breadcrumbs.getBoundingClientRect();

        if (rect.bottom > 20) {
            // Breadcrumbs are still visible.
            tocPanel.style.top = `${rect.bottom + 10}px`;
        }
        else {
            // Breadcrumbs have scrolled away.
            tocPanel.style.top = "20px";
        }
    }

    updateTocPosition();

    window.addEventListener("scroll", updateTocPosition);
    window.addEventListener("resize", updateTocPosition);
});

// Theme Controller (3-mode dropdown: Light / Dark / System with automatic OS sync)
const THEME_STORAGE_KEY = "checkstyle-theme";

const SUN_SVG =
    '<svg class="theme-icon" width="14" height="14" viewBox="0 0 24 24" fill="none"'
    + ' stroke="currentColor" stroke-width="2" stroke-linecap="round"'
    + ' stroke-linejoin="round" aria-hidden="true">'
    + '<circle cx="12" cy="12" r="5"></circle>'
    + '<line x1="12" y1="1" x2="12" y2="3"></line>'
    + '<line x1="12" y1="21" x2="12" y2="23"></line>'
    + '<line x1="4.22" y1="4.22" x2="5.64" y2="5.64"></line>'
    + '<line x1="18.36" y1="18.36" x2="19.78" y2="19.78"></line>'
    + '<line x1="1" y1="12" x2="3" y2="12"></line>'
    + '<line x1="21" y1="12" x2="23" y2="12"></line>'
    + '<line x1="4.22" y1="19.78" x2="5.64" y2="18.36"></line>'
    + '<line x1="18.36" y1="5.64" x2="19.78" y2="4.22"></line></svg>';

const MOON_SVG =
    '<svg class="theme-icon" width="14" height="14" viewBox="0 0 24 24" fill="none"'
    + ' stroke="currentColor" stroke-width="2" stroke-linecap="round"'
    + ' stroke-linejoin="round" aria-hidden="true">'
    + '<path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path></svg>';

const MONITOR_SVG =
    '<svg class="theme-icon" width="14" height="14" viewBox="0 0 24 24" fill="none"'
    + ' stroke="currentColor" stroke-width="2" stroke-linecap="round"'
    + ' stroke-linejoin="round" aria-hidden="true">'
    + '<rect x="2" y="3" width="20" height="14" rx="2" ry="2"></rect>'
    + '<line x1="8" y1="21" x2="16" y2="21"></line>'
    + '<line x1="12" y1="17" x2="12" y2="21"></line></svg>';

const CHECK_SVG =
    '<svg class="theme-check-icon" width="14" height="14" viewBox="0 0 24 24" fill="none"'
    + ' stroke="currentColor" stroke-width="2.5" stroke-linecap="round"'
    + ' stroke-linejoin="round" aria-hidden="true">'
    + '<polyline points="20 6 9 17 4 12"></polyline></svg>';

const THEME_OPTIONS = [
    { value: "light", label: "Light", icon: SUN_SVG },
    { value: "dark", label: "Dark", icon: MOON_SVG },
    { value: "system", label: "System Preference", icon: MONITOR_SVG }
];

let sessionPreference = null;

function getStoredPreference() {
    if (sessionPreference !== null) {
        return sessionPreference;
    }
    try {
        const stored = localStorage.getItem(THEME_STORAGE_KEY);
        if (stored === "light" || stored === "dark" || stored === "system") {
            sessionPreference = stored;
            return stored;
        }
    } catch (e) {
        // Fallback for sandboxed environments or disabled storage
    }
    return "system";
}

function setStoredPreference(pref) {
    sessionPreference = pref;
    try {
        localStorage.setItem(THEME_STORAGE_KEY, pref);
    } catch (e) {
        // Fallback for sandboxed environments or disabled storage
    }
}

function getSystemTheme() {
    if (window.matchMedia && window.matchMedia("(prefers-color-scheme: dark)").matches) {
        return "dark";
    }
    return "light";
}

function getEffectiveTheme(preference) {
    if (preference === "system") {
        return getSystemTheme();
    }
    return preference;
}

function updateThemeUI(preference) {
    const effectiveTheme = getEffectiveTheme(preference);
    document.documentElement.setAttribute("data-theme", effectiveTheme);

    const toggleBtn = document.getElementById("theme-toggle");
    if (toggleBtn) {
        const iconSpan = toggleBtn.querySelector(".theme-toggle-icon");
        if (iconSpan) {
            if (preference === "system") {
                iconSpan.innerHTML = MONITOR_SVG;
            } else if (preference === "dark") {
                iconSpan.innerHTML = MOON_SVG;
            } else {
                iconSpan.innerHTML = SUN_SVG;
            }
        }
        const prefLabel = preference.charAt(0).toUpperCase() + preference.slice(1);
        const effectiveLabel = effectiveTheme.charAt(0).toUpperCase() + effectiveTheme.slice(1);
        const titleText = preference === "system"
            ? "Theme: System Preference (" + effectiveLabel + ") - Click to choose theme"
            : "Theme: " + prefLabel + " - Click to choose theme";
        toggleBtn.setAttribute("aria-label", titleText);
        toggleBtn.title = titleText;
    }

    const dropdown = document.getElementById("theme-dropdown");
    if (dropdown) {
        const items = dropdown.querySelectorAll(".theme-dropdown-item");
        items.forEach(function (item) {
            const val = item.getAttribute("data-theme-value");
            const isChecked = val === preference;
            item.setAttribute("aria-checked", isChecked ? "true" : "false");
            item.tabIndex = isChecked ? 0 : -1;
        });
    }
}

function applyPreference(preference, save) {
    if (save) {
        setStoredPreference(preference);
    }
    updateThemeUI(preference);
}

function openDropdown() {
    const toggleBtn = document.getElementById("theme-toggle");
    const dropdown = document.getElementById("theme-dropdown");
    if (!dropdown || !toggleBtn) {
        return;
    }
    dropdown.removeAttribute("hidden");
    toggleBtn.setAttribute("aria-expanded", "true");

    const checkedItem = dropdown.querySelector('.theme-dropdown-item[aria-checked="true"]');
    const firstItem = dropdown.querySelector(".theme-dropdown-item");
    const target = checkedItem || firstItem;
    if (target) {
        target.focus();
    }
}

function closeDropdown(returnFocus) {
    const toggleBtn = document.getElementById("theme-toggle");
    const dropdown = document.getElementById("theme-dropdown");
    if (!dropdown || !toggleBtn) {
        return;
    }
    dropdown.setAttribute("hidden", "");
    toggleBtn.setAttribute("aria-expanded", "false");
    if (returnFocus) {
        toggleBtn.focus();
    }
}

function isDropdownOpen() {
    const dropdown = document.getElementById("theme-dropdown");
    return dropdown && !dropdown.hasAttribute("hidden");
}

function injectThemeToggle() {
    const breadcrumb = document.querySelector("#breadcrumbs .breadcrumb");
    if (!breadcrumb || document.getElementById("theme-toggle-item")) {
        return;
    }

    const li = document.createElement("li");
    li.id = "theme-toggle-item";
    li.className = "pull-right theme-toggle-container";

    const button = document.createElement("button");
    button.id = "theme-toggle";
    button.type = "button";
    button.className = "theme-toggle-btn";
    button.setAttribute("aria-haspopup", "menu");
    button.setAttribute("aria-expanded", "false");
    button.setAttribute("aria-controls", "theme-dropdown");

    const iconSpan = document.createElement("span");
    iconSpan.className = "theme-toggle-icon";
    button.appendChild(iconSpan);

    const dropdown = document.createElement("div");
    dropdown.id = "theme-dropdown";
    dropdown.className = "theme-dropdown-menu";
    dropdown.setAttribute("role", "menu");
    dropdown.setAttribute("aria-label", "Theme options");
    dropdown.setAttribute("hidden", "");

    THEME_OPTIONS.forEach(function (opt) {
        const item = document.createElement("button");
        item.type = "button";
        item.className = "theme-dropdown-item";
        item.setAttribute("role", "menuitemradio");
        item.setAttribute("data-theme-value", opt.value);
        item.setAttribute("aria-checked", "false");
        item.tabIndex = -1;

        const iconEl = document.createElement("span");
        iconEl.className = "theme-item-icon";
        iconEl.innerHTML = opt.icon;

        const labelEl = document.createElement("span");
        labelEl.className = "theme-item-label";
        labelEl.textContent = opt.label;

        const checkEl = document.createElement("span");
        checkEl.className = "theme-item-check";
        checkEl.setAttribute("aria-hidden", "true");
        checkEl.innerHTML = CHECK_SVG;

        item.appendChild(iconEl);
        item.appendChild(labelEl);
        item.appendChild(checkEl);

        item.addEventListener("click", function (e) {
            e.preventDefault();
            applyPreference(opt.value, true);
            closeDropdown(true);
        });

        item.addEventListener("keydown", function (e) {
            const items = Array.from(dropdown.querySelectorAll(".theme-dropdown-item"));
            const currentIndex = items.indexOf(item);

            if (e.key === "ArrowDown") {
                e.preventDefault();
                const next = (currentIndex + 1) % items.length;
                items[next].focus();
            } else if (e.key === "ArrowUp") {
                e.preventDefault();
                const prev = (currentIndex - 1 + items.length) % items.length;
                items[prev].focus();
            } else if (e.key === "Home") {
                e.preventDefault();
                items[0].focus();
            } else if (e.key === "End") {
                e.preventDefault();
                items[items.length - 1].focus();
            } else if (e.key === "Enter" || e.key === " ") {
                e.preventDefault();
                applyPreference(opt.value, true);
                closeDropdown(true);
            } else if (e.key === "Escape") {
                e.preventDefault();
                closeDropdown(true);
            } else if (e.key === "Tab") {
                closeDropdown(false);
                button.focus();
            }
        });

        dropdown.appendChild(item);
    });

    button.addEventListener("click", function (event) {
        event.preventDefault();
        event.stopPropagation();
        if (isDropdownOpen()) {
            closeDropdown(false);
        } else {
            openDropdown();
        }
    });

    button.addEventListener("keydown", function (e) {
        if (e.key === "ArrowDown" || e.key === "Enter" || e.key === " ") {
            e.preventDefault();
            if (!isDropdownOpen()) {
                openDropdown();
            } else {
                closeDropdown(false);
            }
        } else if (e.key === "ArrowUp") {
            e.preventDefault();
            if (!isDropdownOpen()) {
                openDropdown();
                const items = dropdown.querySelectorAll(".theme-dropdown-item");
                if (items.length > 0) {
                    items[items.length - 1].focus();
                }
            }
        } else if (e.key === "Escape" && isDropdownOpen()) {
            e.preventDefault();
            closeDropdown(true);
        }
    });

    document.addEventListener("click", function (event) {
        if (!li.contains(event.target) && isDropdownOpen()) {
            closeDropdown(false);
        }
    });

    li.appendChild(button);
    li.appendChild(dropdown);

    const hamburger = document.getElementById("hamburger");
    if (hamburger && hamburger.parentNode === breadcrumb) {
        breadcrumb.insertBefore(li, hamburger);
    } else {
        breadcrumb.appendChild(li);
    }

    updateThemeUI(getStoredPreference());
}

function initTheme() {
    applyPreference(getStoredPreference(), false);
    injectThemeToggle();
}

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", initTheme);
} else {
    initTheme();
}

window.addEventListener("load", function () {
    injectThemeToggle();
});

if (window.matchMedia) {
    const colorSchemeMedia = window.matchMedia("(prefers-color-scheme: dark)");
    const handleMediaChange = function () {
        if (getStoredPreference() === "system") {
            applyPreference("system", false);
        }
    };
    if (colorSchemeMedia.addEventListener) {
        colorSchemeMedia.addEventListener("change", handleMediaChange);
    } else if (colorSchemeMedia.addListener) {
        colorSchemeMedia.addListener(handleMediaChange);
    }
}

window.addEventListener("storage", function (event) {
    if (event.key === THEME_STORAGE_KEY) {
        sessionPreference = (event.newValue === "light" || event.newValue === "dark" || event.newValue === "system")
            ? event.newValue : null;
        applyPreference(getStoredPreference(), false);
    }
});
