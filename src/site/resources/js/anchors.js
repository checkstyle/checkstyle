/*jslint browser: true*/
/*global window */

function createAnchorElement(url, name, relativePath, anchorItem) {
    const a = document.createElement("a");
    a.setAttribute("href", url + "#" + name);
    const image = document.createElement("img");
    image.setAttribute("src", `${relativePath}/images/anchor.png`);
    a.appendChild(image);
    const anchor = document.createElement("div");
    anchor.className = "anchor";
    anchor.appendChild(a);
    anchorItem.appendChild(anchor);
}

(function () {
    window.addEventListener("load", function () {
        let url = window.location.href;
        const position = url.indexOf("#");
        if (position !== -1) {
            url = url.substring(0, position);
        }
        const relativePath = document.querySelector('script[src*="anchors.js"]')
                            .attributes.src.textContent.replace(/\/js\/anchors.js/, '');
        [].forEach.call(document.querySelectorAll("h1, h2"), function (anchorItem) {
            if (anchorItem.closest("#bannerRight") || anchorItem.closest("#bannerLeft")) {
                return;
            }
            createAnchorElement(url,
                anchorItem.previousSibling.previousElementSibling.id,
                relativePath,
                anchorItem);
        });
        [].forEach.call(document.getElementsByTagName("h3"), function (anchorItem) {
            let name;
            if (anchorItem.parentNode.id) {
                name = anchorItem.parentNode.id;
            } else {
                name = anchorItem.childNodes[0].name;
            }
            createAnchorElement(url, name, relativePath, anchorItem);
        });
        [].forEach.call(document.querySelectorAll('p[id^="Example"]'), function (exampleDiv) {
            const a = document.createElement("a");
            a.setAttribute("href", url + "#" + exampleDiv.id);
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
