/*jslint browser: true*/
/*global window */
(function () {
    "use strict";
    window.addEventListener("load", function () {
        var headers = document.getElementsByTagName("h2");
        [].forEach.call(headers, function (header) {
            var csVersion = header.childNodes[0].name.replace("Release_", "");
            var link = `https:\/\/raw.githubusercontent.com/checkstyle/checkstyle/checkstyle-${csVersion}/src/main/resources/google_checks.xml`
            var p = document.createElement("p");

            var a = document.createElement("a");
            a.setAttribute("href", link);
            a.innerText = "Google config";
            p.appendChild(a)
            header.parentNode.insertBefore(p, header.nextSibling.nextSibling );
        });

    });
}());

