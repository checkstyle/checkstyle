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

        var addAnchor = function (anchorItem) {
            var name;
            if (!!anchorItem.parentNode["id"]) {
                name = anchorItem.parentNode["id"]
            } else {
                name = anchorItem.childNodes[0].name;
            }
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
        };
        [].forEach.call(document.getElementsByTagName("h2"), addAnchor);
        [].forEach.call(document.getElementsByTagName("h3"), addAnchor);
    });
}());

