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

    // If in mobile view use margin as defined in site.css
    if (window.innerWidth < 823) {
        bodyColumn.style.marginLeft = '1.5em';
        return;
    }

    // Else calculate margin based on left column width
    const leftColumnWidth = leftColumn.offsetWidth;
    bodyColumn.style.marginLeft = `${leftColumnWidth + 27}px`;
}

window.addEventListener('load', setBodyColumnMargin);
window.addEventListener('resize', setBodyColumnMargin);
