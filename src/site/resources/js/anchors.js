'use strict';

function main() {
    var url = window.location.href;
    var anchors = document.getElementsByTagName('h2');
    var mass = [];
    for (var i = 0; i < anchors.length; i++) {
        mass.push(anchors[i].childNodes[0].name);
    }
    var links = [];
    for(i = 0; i < mass.length; i++) {
        links.push('' + url + '#' + mass[i] + '');
    }
    for(i = 0; i < anchors.length; i++) {
        var anchor = document.createElement('div');
        anchor.className = 'anchor';
        anchor.innerHTML += '<a href="' + links[i] + '"><img src="images/anchor.png"></a>';
        anchors[i].appendChild(anchor);
    }

}

window.addEventListener('load', main);
