/*jslint browser: true*/
/*jshint esversion: 6*/
/*global window */
let scrollButton;
const scrollDistanceToButtonVisibility = 500;

// noinspection JSLint
window.addEventListener("load", function () {
    scrollButton = document.querySelector("a[title=\"toTop\"]");
    scrollButton.innerText = "To Top";
    scrollButton.style.display = "none";

    // noinspection JSLint
    scrollButton.addEventListener("click", function (event) {
        event.preventDefault();
        document.documentElement.scrollTop = 0;
    });
});

// noinspection JSLint
window.addEventListener("scroll", function () {
    if (document.documentElement.scrollTop > scrollDistanceToButtonVisibility) {
        scrollButton.style.display = "block";
    } else {
        scrollButton.style.display = "none";
    }
});
