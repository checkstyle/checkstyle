window.onload = () => {
    const currentUrl = window.location.href;
    const urlObj = new URL(currentUrl);
    const pathSegments = urlObj.pathname.split("/");
    const lastSegment = pathSegments[pathSegments.length - 1];
    const extractedUrl =  `${lastSegment}${urlObj.hash}`;

    switch (extractedUrl) {
        case "checks.html":
            window.location.replace("https://checkstyle.org/checks");
            break;
        case "checks":
            window.location.replace("https://checkstyle.org/checks");
            break;
        case "config_coding.html":
            window.location.replace("https://checkstyle.org/checks/coding/");
            break;
        case "config_coding.html#VariableDeclarationUsageDistance":
            window.location.replace("https://checkstyle.org/checks/coding/variabledeclarationusagedistance.html");
            break;
    }
}
