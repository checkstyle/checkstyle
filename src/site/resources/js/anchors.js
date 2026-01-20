/*jslint browser: true*/
/*jslint es6: true */
/*jshint esversion: 6 */
/*global window */
(function () {
    "use strict";

    function showCopiedFeedback(element) {
        const feedback = document.createElement("div");
        feedback.textContent = "Copied!";
        feedback.className = "anchor-feedback";

        document.body.appendChild(feedback);

        setTimeout(function () {
            feedback.remove();
        }, 2000);
    }

    function createAnchor(parentElement, link, name, relativePath, feedbackTarget) {
        const a = document.createElement("a");
        a.setAttribute("href", link);

        a.addEventListener("click", function (event) {
            event.preventDefault();

            if (navigator.clipboard) {
                navigator.clipboard.writeText(link).then(function () {
                    showCopiedFeedback(feedbackTarget);
                }).catch(function (err) {
                    console.warn("Failed to copy link to clipboard:", err);
                });
            } else {
                console.warn("Clipboard API not available");
            }

            // Update URL without page jump
            history.replaceState(null, "", "#" + name);
        });

        const image = document.createElement("img");
        image.setAttribute("src", relativePath + "/images/anchor.png");

        const anchor = document.createElement("div");
        anchor.className = "anchor";

        a.appendChild(image);
        anchor.appendChild(a);
        parentElement.appendChild(anchor);
    }

    window.addEventListener("load", function () {
        let url = window.location.href;
        const position = url.indexOf("#");

        if (position !== -1) {
            url = url.substring(0, position);
        }

        const scriptElement = document.querySelector('script[src*="anchors.js"]');
        const scriptElementSrc = scriptElement.attributes.src.textContent;
        const relativePath = scriptElementSrc.replace(/\/js\/anchors.js/, "");

        // h1 / h2
        const anchors = document.querySelectorAll("h1, h2");
        [].forEach.call(anchors, function (anchorItem) {

            if (anchorItem.closest("#bannerRight") || anchorItem.closest("#bannerLeft")) {
                return;
            }

            const name = anchorItem.previousSibling.previousElementSibling.id;
            const link = url + "#" + name;

            createAnchor(anchorItem, link, name, relativePath, anchorItem);
        });

        // h3
        const anchorsSubSection = document.getElementsByTagName("h3");
        [].forEach.call(anchorsSubSection, function (anchorItem) {
            let name;
            if (anchorItem.parentNode.id) {
                name = anchorItem.parentNode.id;
            } else {
                name = anchorItem.childNodes[0].name;
            }

            const link = url + "#" + name;
            createAnchor(anchorItem, link, name, relativePath, anchorItem);
        });

        // Example sections
        const exampleDivs = document.querySelectorAll('p[id^="Example"]');
        [].forEach.call(exampleDivs, function (exampleDiv) {
            const name = exampleDiv.id;
            const link = url + "#" + name;

            createAnchor(exampleDiv, link, name, relativePath, exampleDiv);
        });
    });

}());
