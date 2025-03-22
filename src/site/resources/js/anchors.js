/*jslint browser: true*/
/*global window */
(function () {
    "use strict";
    window.addEventListener("load", function () {
        var url = window.location.href;
        var position = url.indexOf("#");

        if (position !== -1) {
            url = url.substring(0, position);
        }

        const scriptElement = document.querySelector('script[src*="anchors.js"]');
        const scriptElementSrc = scriptElement.attributes.src.textContent;
        const relativePath = scriptElementSrc.replace(/\/js\/anchors.js/, '');

        var anchors = document.querySelectorAll("h1, h2");
        [].forEach.call(anchors, function (anchorItem) {

            if (anchorItem.closest("#bannerRight") || anchorItem.closest("#bannerLeft")) {
                return;
            }

            var name = anchorItem.previousSibling.previousElementSibling.id;
            var link = "" + url + "#" + name + "";

            var a = document.createElement("a");
            a.setAttribute("href", link);

            var image = document.createElement("img");
            image.setAttribute("src", `${relativePath}/images/anchor.png`);

            var anchor = document.createElement("div");
            anchor.className = "anchor";

            a.appendChild(image);
            anchor.appendChild(a);
            anchorItem.appendChild(anchor);
        });

        var anchorsSubSection = document.getElementsByTagName("h3");
        [].forEach.call(anchorsSubSection, function (anchorItem) {
            var name;
            if (anchorItem.parentNode.id) {
                name = anchorItem.parentNode.id;
            } else {
                name = anchorItem.childNodes[0].name;
            }
            var link = "" + url + "#" + name + "";

            var a = document.createElement("a");
            a.setAttribute("href", link);

            var image = document.createElement("img");
            image.setAttribute("src", `${relativePath}/images/anchor.png`);

            var anchor = document.createElement("div");
            anchor.className = "anchor";

            a.appendChild(image);
            anchor.appendChild(a);
            anchorItem.appendChild(anchor);
        });

        const exampleDivs = document.querySelectorAll('p[id^="Example"]');
        [].forEach.call(exampleDivs, function (exampleDiv) {
            const name = exampleDiv.id;
            const link = "" + url + "#" + name + "";

            const a = document.createElement("a");
            a.setAttribute("href", link);

            const image = document.createElement("img");
            image.setAttribute("src", `${relativePath}/images/anchor.png`);

            const anchor = document.createElement("div");
            anchor.className = "anchor";

            a.appendChild(image);
            anchor.appendChild(a);
            exampleDiv.appendChild(anchor);
        });
    });
}());