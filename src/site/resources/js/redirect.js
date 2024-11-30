window.onload = () => {
    const currentUrl = window.location.href;
    switch (currentUrl) {
        case "https://checkstyle.org/checks/":
            window.location.href = "https://checkstyle.org/checks";
            break;
        case "https://checkstyle.org/config_coding.html":
            window.location.href = "https://checkstyle.org/checks/coding/";
            break;
        case "https://checkstyle.org/config_coding.html#VariableDeclarationUsageDistance":
            window.location.href = "https://checkstyle.org/checks/coding/variabledeclarationusagedistance.html";
            break;
    }
}
