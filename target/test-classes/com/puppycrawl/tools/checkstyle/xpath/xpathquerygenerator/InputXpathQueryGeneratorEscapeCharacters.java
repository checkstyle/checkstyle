package com.puppycrawl.tools.checkstyle.xpath.xpathquerygenerator;

public class InputXpathQueryGeneratorEscapeCharacters {
    String testOne = "<>'\"&abc;&lt;\u0080\n";

    String testTwo = "&#0;&#X0\u0001\r";
}
