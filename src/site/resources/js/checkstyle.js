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

// Theme Controller (Light / Dark / Auto system mode)
const THEME_STORAGE_KEY = "checkstyle-theme";

const SUN_SVG = '<svg class="theme-icon" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><circle cx="12" cy="12" r="5"></circle><line x1="12" y1="1" x2="12" y2="3"></line><line x1="12" y1="21" x2="12" y2="23"></line><line x1="4.22" y1="4.22" x2="5.64" y2="5.64"></line><line x1="18.36" y1="18.36" x2="19.78" y2="19.78"></line><line x1="1" y1="12" x2="3" y2="12"></line><line x1="21" y1="12" x2="23" y2="12"></line><line x1="4.22" y1="19.78" x2="5.64" y2="18.36"></line><line x1="18.36" y1="5.64" x2="19.78" y2="4.22"></line></svg>';

const MOON_SVG = '<svg class="theme-icon" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path></svg>';

const MONITOR_SVG = '<svg class="theme-icon" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><rect x="2" y="3" width="20" height="14" rx="2" ry="2"></rect><line x1="8" y1="21" x2="16" y2="21"></line><line x1="12" y1="17" x2="12" y2="21"></line></svg>';

function getStoredTheme() {
    try {
        return localStorage.getItem(THEME_STORAGE_KEY) || "system";
    } catch (e) {
        return "system";
    }
}

function setStoredTheme(theme) {
    try {
        localStorage.setItem(THEME_STORAGE_KEY, theme);
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

function updateThemeToggleUI(storedTheme, effectiveTheme) {
    const toggleBtn = document.getElementById("theme-toggle");
    if (!toggleBtn) {
        return;
    }
    const iconSpan = toggleBtn.querySelector(".theme-toggle-icon");
    const textSpan = toggleBtn.querySelector(".theme-toggle-text");

    let label = "Auto";
    let icon = MONITOR_SVG;
    let nextTheme = "light";

    if (storedTheme === "light") {
        label = "Light";
        icon = SUN_SVG;
        nextTheme = "dark";
    } else if (storedTheme === "dark") {
        label = "Dark";
        icon = MOON_SVG;
        nextTheme = "auto";
    } else {
        label = "Auto";
        icon = MONITOR_SVG;
        nextTheme = "light";
    }

    if (iconSpan) {
        iconSpan.innerHTML = icon;
    }
    if (textSpan) {
        textSpan.textContent = label;
    }

    const modeDescription = storedTheme === "system"
        ? `Auto (${effectiveTheme})`
        : label;
    toggleBtn.setAttribute("aria-label", `Current theme: ${modeDescription}. Click to switch to ${nextTheme}.`);
    toggleBtn.title = `Theme: ${modeDescription}. Click to switch to ${nextTheme}.`;
}

function applyTheme(theme) {
    const effectiveTheme = theme === "system" ? getSystemTheme() : theme;
    document.documentElement.setAttribute("data-theme", effectiveTheme);
    document.documentElement.setAttribute("data-theme-preference", theme);
    updateThemeToggleUI(theme, effectiveTheme);
}

function cycleTheme() {
    const current = getStoredTheme();
    let nextTheme = "system";
    if (current === "system") {
        nextTheme = "light";
    } else if (current === "light") {
        nextTheme = "dark";
    } else {
        nextTheme = "system";
    }
    setStoredTheme(nextTheme);
    applyTheme(nextTheme);
}

function injectThemeToggle() {
    const breadcrumb = document.querySelector("#breadcrumbs .breadcrumb");
    if (!breadcrumb || document.getElementById("theme-toggle-item")) {
        return;
    }

    const li = document.createElement("li");
    li.id = "theme-toggle-item";
    li.className = "pull-right";

    const divider = document.createElement("span");
    divider.className = "divider";
    divider.textContent = "|";

    const button = document.createElement("button");
    button.id = "theme-toggle";
    button.type = "button";
    button.className = "theme-toggle-btn";

    const iconSpan = document.createElement("span");
    iconSpan.className = "theme-toggle-icon";

    const textSpan = document.createElement("span");
    textSpan.className = "theme-toggle-text";

    button.appendChild(iconSpan);
    button.appendChild(textSpan);

    button.addEventListener("click", function (event) {
        event.preventDefault();
        cycleTheme();
    });

    li.appendChild(divider);
    li.appendChild(button);

    const publishDate = document.getElementById("publishDate");
    if (publishDate) {
        breadcrumb.insertBefore(li, publishDate);
    } else {
        breadcrumb.appendChild(li);
    }

    const stored = getStoredTheme();
    const effective = stored === "system" ? getSystemTheme() : stored;
    updateThemeToggleUI(stored, effective);
}

function initTheme() {
    const stored = getStoredTheme();
    applyTheme(stored);
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
        if (getStoredTheme() === "system") {
            applyTheme("system");
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
        applyTheme(event.newValue || "system");
    }
});
