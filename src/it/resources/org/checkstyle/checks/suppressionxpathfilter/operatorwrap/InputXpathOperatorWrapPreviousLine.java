package org.checkstyle.checks.suppressionxpathfilter.operatorwrap;

public class InputXpathOperatorWrapPreviousLine {
    int b
          = 10; // warn
    int c =
             10;
}
