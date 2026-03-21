package com.puppycrawl.tools.checkstyle.grammar;

public class InputCharLiteralSurrogatePair {
    String X = "ища"; // parser is ok with this line
    String Y = "н"; // parser is ok with this line
    char Z = 'н'; // parser is NOT ok with this line
}
