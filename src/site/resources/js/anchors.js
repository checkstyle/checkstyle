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

        var anchors = document.getElementsByTagName("h2");
        [].forEach.call(anchors, function (anchorItem) {
            var name = anchorItem.childNodes[0].name;
            var link = "" + url + "#" + name + "";

            var a = document.createElement("a");
            a.setAttribute("href", link);

            var image = document.createElement("img");
            image.setAttribute("src", "images/anchor.png");

            var anchor = document.createElement("div");
            anchor.className = "anchor";

            a.appendChild(image);
            anchor.appendChild(a);
            anchorItem.appendChild(anchor);
        });

        var anchorsSubSection = document.getElementsByTagName("h3");
        [].forEach.call(anchorsSubSection, function (anchorItem) {
            var name = anchorItem.parentNode.previousElementSibling.name;
            var link = "" + url + "#" + name + "";

            var a = document.createElement("a");
            a.setAttribute("href", link);

            var image = document.createElement("img");
            image.setAttribute("src", "images/anchor.png");

            var anchor = document.createElement("div");
            anchor.className = "anchor";

            a.appendChild(image);
            anchor.appendChild(a);
            anchorItem.appendChild(anchor);
        });
    });
}());

