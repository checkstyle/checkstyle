/*jslint browser: true */
/*jslint es6: true */
/*jshint esversion: 6 */
/*global window */
let scrollButton;
const scrollDistanceToButtonVisibility = 500;

window.addEventListener("load", function () {
    "use strict";
    scrollButton = document.querySelector("a[title=\"toTop\"]");
    scrollButton.innerText = "To Top";
    scrollButton.style.display = "none";

    scrollButton.addEventListener("click", function (event) {
        event.preventDefault();
        document.documentElement.scrollTop = 0;
    });
});

window.addEventListener("load", function () {
    const currentUrl = window.location.href;

    if (currentUrl.endsWith("/checks/") || currentUrl.endsWith("/checks/index.html")) {
        window.location.replace("../checks.html");
    }
    else if (document.title.startsWith("checkstyle – Redirecting to checks/")) {
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

let isMenuVisible = false;
window.addEventListener("scroll", function () {
    "use strict";
    if (document.documentElement.scrollTop > scrollDistanceToButtonVisibility) {
        scrollButton.style.display = "block";
    } else {
        scrollButton.style.display = "none";
    }
});

function setBodyColumnMargin() {
    const leftColumn = document.querySelector('#leftColumn');
    const bodyColumn = document.querySelector('#bodyColumn');

    if (document.getElementById("hamburger")) {
        resetStyling();
    }

    console.log("after if condition")
    console.log("width: ", document.body.clientWidth)
    console.log("height: ", document.body.clientHeight)

    if (isMenuVisible) {
        document.getElementById("leftColumn").style.display = "block";
    }

    // If in mobile view use margin as defined in site.css
    if (window.innerWidth < 823) {
        bodyColumn.style.marginLeft = '1.5em';
        setCollapsableMenuButton();
        return;
    }

    // Else calculate margin based on left column width
    const leftColumnWidth = leftColumn.offsetWidth;
    bodyColumn.style.marginLeft = `${leftColumnWidth + 27}px`;
}

function setCollapsableMenuButton() {
    const menu = document.getElementById("leftColumn");
    const breadcrumbs = document.getElementById("breadcrumbs");
    const hamburger = document.createElement('div');
    hamburger.id = 'hamburger'

    for (let i = 0; i < 3; i++) {
        const line = document.createElement('span');
        hamburger.appendChild(line);
    }

    const xright = document.getElementsByClassName("xright")[0];
    xright.appendChild(document.createTextNode(" | "));
    xright.appendChild(hamburger);

    hamburger.addEventListener("click", (e) => {
        e.preventDefault();

        menu.style.top = `${breadcrumbs.offsetHeight + 10}px`;
        menu.style.height = `calc(100vh - ${breadcrumbs.offsetHeight + 10}px)`;

        if (hamburger.classList.contains('openMenu')) {
            hamburger.classList.remove('openMenu');
            menu.style.display = "none";
            isMenuVisible = true;
        }
        else {
            hamburger.classList.add('openMenu');
            menu.style.display = "block";
            isMenuVisible = false;
        }
    })
}

function resetStyling() {
    const hamburger = document.getElementById("hamburger");
    hamburger.remove()

    const menu = document.getElementById("leftColumn");
    menu.style.display = "block";
    menu.style.removeProperty("top");
    menu.style.removeProperty("height");

    const xright = document.getElementsByClassName("xright")[0];
    xright.lastChild.remove();
}

window.addEventListener('load', setBodyColumnMargin);
window.addEventListener('resize', setBodyColumnMargin);
