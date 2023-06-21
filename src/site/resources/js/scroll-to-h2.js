window.addEventListener("load", function () {
    if (window.innerWidth > 823) {
        return;
    }

    const anchorIsAlreadySelected = document.URL.includes("#");
    if (anchorIsAlreadySelected) {
        return;
    }

    const elements = document.querySelectorAll("h2 > a[name]");
    if (elements.length > 0) {
        const element = elements[0];
        element.scrollIntoView({ behavior: 'smooth' });
    }
});
