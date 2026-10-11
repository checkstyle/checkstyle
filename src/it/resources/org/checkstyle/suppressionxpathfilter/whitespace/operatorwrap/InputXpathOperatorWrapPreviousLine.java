package org.checkstyle.suppressionxpathfilter.whitespace.operatorwrap;

public class InputXpathOperatorWrapPreviousLine {
    int b
          = 10; // warn
    int c =
             10;
}
