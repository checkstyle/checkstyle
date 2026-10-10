package org.checkstyle.suppressionxpathfilter.indentation.openjdklinewrapping;

public class InputXpathOpenjdkLineWrappingReturn {
    int method(int value) {
        return value
         + 1; // warn
    }
}
