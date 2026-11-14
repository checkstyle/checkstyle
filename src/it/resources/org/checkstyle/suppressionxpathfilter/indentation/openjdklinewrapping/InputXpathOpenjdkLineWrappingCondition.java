/*
OpenjdkLineWrapping


*/


package org.checkstyle.suppressionxpathfilter.indentation.openjdklinewrapping;

public class InputXpathOpenjdkLineWrappingCondition {
    void method(boolean first, boolean second) {
        if (first ||
         second) { // violation 'incorrect indentation level 9'
        }
    }
}
