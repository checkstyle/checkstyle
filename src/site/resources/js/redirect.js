document.addEventListener("DOMContentLoaded", function () {
    const currentUrl = window.location.href;

    if (currentUrl === "https://checkstyle.org/config_javadoc.html#JavadocType") {
        window.location.replace = "https://checkstyle.org/checks/javadoc/javadoctype.html";
    } else if (currentUrl === "https://checkstyle.org/checks/") {
        window.location.replace = "https://checkstyle.org/checks";
    }
});
