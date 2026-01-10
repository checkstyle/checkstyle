/*jslint browser: true*/
/*global window */
(function () {
    "use strict";

    function showCopiedFeedback(element) {
        var feedback = document.createElement("span");
        feedback.textContent = "copied";
        feedback.className = "anchor-feedback";

        element.appendChild(feedback);

        setTimeout(function () {
            feedback.remove();
        }, 1200);
    }

    function createAnchor(parentElement, link, name, relativePath, feedbackTarget) {
        var a = document.createElement("a");
        a.setAttribute("href", link);

        a.addEventListener("click", function (event) {
            event.preventDefault();

            if (navigator.clipboard) {
                navigator.clipboard.writeText(link);
                showCopiedFeedback(feedbackTarget);
            }

            // Update URL without page jump
            history.replaceState(null, "", "#" + name);
        });

        var image = document.createElement("img");
        image.setAttribute("src", relativePath + "/images/anchor.png");

        var anchor = document.createElement("div");
        anchor.className = "anchor";

        a.appendChild(image);
        anchor.appendChild(a);
        parentElement.appendChild(anchor);
    }

    window.addEventListener("load", function () {
        var url = window.location.href;
        var position = url.indexOf("#");

        if (position !== -1) {
            url = url.substring(0, position);
        }

        var scriptElement = document.querySelector('script[src*="anchors.js"]');
        var scriptElementSrc = scriptElement.attributes.src.textContent;
        var relativePath = scriptElementSrc.replace(/\/js\/anchors.js/, "");

        // h1 / h2
        var anchors = document.querySelectorAll("h1, h2");
        [].forEach.call(anchors, function (anchorItem) {

            if (anchorItem.closest("#bannerRight") || anchorItem.closest("#bannerLeft")) {
                return;
            }

            var name = anchorItem.previousSibling.previousElementSibling.id;
            var link = url + "#" + name;

            createAnchor(anchorItem, link, name, relativePath, anchorItem);
        });

        // h3
        var anchorsSubSection = document.getElementsByTagName("h3");
        [].forEach.call(anchorsSubSection, function (anchorItem) {
            var name;
            if (anchorItem.parentNode.id) {
                name = anchorItem.parentNode.id;
            } else {
                name = anchorItem.childNodes[0].name;
            }

            var link = url + "#" + name;
            createAnchor(anchorItem, link, name, relativePath, anchorItem);
        });

        // Example sections
        var exampleDivs = document.querySelectorAll('p[id^="Example"]');
        [].forEach.call(exampleDivs, function (exampleDiv) {
            var name = exampleDiv.id;
            var link = url + "#" + name;

            createAnchor(exampleDiv, link, name, relativePath, exampleDiv);
        });
    });

}());
