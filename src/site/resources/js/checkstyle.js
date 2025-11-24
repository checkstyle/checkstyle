/*jslint browser: true */
/*jslint es6: true */
/*jshint esversion: 6 */
/*global window */
let scrollButton;
const scrollDistanceToButtonVisibility = 500;

window.addEventListener("load", function () {
    scrollButton = document.querySelector(".pull-right > a");
    scrollButton.innerText = "To Top";
    scrollButton.style.display = "none";

    scrollButton.addEventListener("click", function (event) {
        event.preventDefault();
        document.documentElement.scrollTop = 0;
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const url = new URL(window.location.href);
    if (!url.pathname.endsWith("/releasenotes.html")
            || !url.hash.startsWith("#Release_")
            || document.getElementById(url.hash.replace("#", ""))) {
        return;
    }

    const version = url.hash.split('_')[1];
    const versionParts = version.split(".");
    if (!versionParts.length >= 2) {
        return;
    }

    let major = parseInt(versionParts[0], 10);
    let minor = parseInt(versionParts[1], 10);

    if (major >= 1 && major < 6) {
        window.location.replace(`./releasenotes_old_1-0_5-9.html${url.hash}`);
    }
    else if (major === 6 || major === 7) {
        window.location.replace(`./releasenotes_old_6-0_7-8.html${url.hash}`);
    }
    else if (major === 8 && minor >= 0 && minor <= 34) {
        window.location.replace(`./releasenotes_old_8-0_8-34.html${url.hash}`);
    }
    else if (major >= 8 && major <= 10) {
        window.location.replace(`./releasenotes_old_8-35_10-26.html${url.hash}`);
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
});

// for newer version of site.
window.addEventListener("load", function () {
    const externalLinks = document.querySelectorAll(".externalLink");
    externalLinks.forEach((link) => {
        link.setAttribute("target", "_blank");
    });
});

window.addEventListener("scroll", function () {
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
    document.querySelector("#leftColumn").removeAttribute("style");
    document.querySelector("body").removeAttribute("style");
    document.querySelector("#bodyColumn").style.removeProperty("position");
    document.querySelector("#hamburger").remove();
    document.querySelector(".xright").lastChild.remove();
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
