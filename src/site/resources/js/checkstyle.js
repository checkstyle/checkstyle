/*esversion: 6*/
/*global window */
let scrollButton;
const scrollDistanceToButtonVisibility = 500;

window.addEventListener("load", () => {
    scrollButton = document.querySelector("a[title=&quot;toTop&quot;]");
    scrollButton.innerText = "To Top";
    scrollButton.style.display = "none";

    scrollButton.addEventListener("click", (event) => {
        event.preventDefault();
        document.documentElement.scrollTop = 0;
    });
});

window.addEventListener("scroll", () => {
    if (document.documentElement.scrollTop > scrollDistanceToButtonVisibility) {
        scrollButton.style.display = "block";
    } else {
        scrollButton.style.display = "none";
    }
});