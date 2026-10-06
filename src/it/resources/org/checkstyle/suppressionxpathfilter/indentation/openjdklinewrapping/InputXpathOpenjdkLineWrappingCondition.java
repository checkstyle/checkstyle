package org.checkstyle.suppressionxpathfilter.indentation.openjdklinewrapping;

public class InputXpathOpenjdkLineWrappingCondition {
    void method(boolean first, boolean second) {
        if (first ||
         second) { // warn
        }
    }
}
