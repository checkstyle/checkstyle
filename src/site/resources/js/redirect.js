window.onload = () => {
    const currentUrl = window.location.href;
    if (currentUrl.endsWith("/checks/") || currentUrl.endsWith("/checks/index.html")) {
        window.location.replace("../checks.html");
    }
    else if (document.title.startsWith("checkstyle – config_")) {
        const urlObj = new URL(currentUrl);
        const pathSegments = urlObj.pathname.split("/");
        const lastSegment = pathSegments[pathSegments.length - 1];
        const extractedUrl =  `/${lastSegment}${urlObj.hash}`;
        const pageTitles = new Map([
            ["checkstyle – config_coding", redirectConfigCodingUrls],
            ["checkstyle – config_header", redirectConfigHeaderUrls],
        ])

        const redirectFunc = pageTitles.get(document.title);
        redirectFunc(extractedUrl);
    }
}

const redirectConfigCodingUrls = (url) => {
    switch (url) {
        case "/config_coding.html":
            window.location.replace("./checks/coding/");
            break;
        case "/config_coding.html#VariableDeclarationUsageDistance":
            window.location.replace("./checks/coding/variabledeclarationusagedistance.html");
            break;
    }
};

const redirectConfigHeaderUrls = (url) => {
    switch (url) {
        case "/config_header.html":
            window.location.replace("./checks/header/");
            break;
        case "/config_header.html#RegexpHeader":
            window.location.replace("./checks/header/regexpheader.html");
            break;
    }
};
