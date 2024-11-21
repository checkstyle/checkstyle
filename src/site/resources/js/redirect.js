window.onload = () => {
    const currentUrl = window.location.href;
    const urlObj = new URL(currentUrl);
    const pathSegments = urlObj.pathname.split("/");
    const lastSegment = pathSegments[pathSegments.length - 1];
    const extractedUrl =  `/${lastSegment}${urlObj.hash}`;

    if (currentUrl === "https://checkstyle.org/checks/") {
        window.location.replace("https://checkstyle.org/checks");
    }
    else if (extractedUrl.match(/config_coding.html*/)) {
        checkConfigCodingUrls(extractedUrl);
    }
    else if (extractedUrl.match(/config_header.html*/)) {
        checkConfigHeadersUrls(extractedUrl);
    }
}

const checkConfigCodingUrls = (url) => {
    switch (url) {
        case "/config_coding.html":
            window.location.replace("https://checkstyle.org/checks/coding/");
            break;
        case "/config_coding.html#VariableDeclarationUsageDistance":
            window.location.replace("https://checkstyle.org/checks/coding/variabledeclarationusagedistance.html");
            break;
    }
};

const checkConfigHeadersUrls = (url) => {
    switch (url) {
        case "/config_header.html":
            window.location.replace("https://checkstyle.org/checks/header/");
            break;
        case "/config_header.html#RegexpHeader":
            window.location.replace("https://checkstyle.org/checks/header/regexpheader.html");
            break;
    }
};
