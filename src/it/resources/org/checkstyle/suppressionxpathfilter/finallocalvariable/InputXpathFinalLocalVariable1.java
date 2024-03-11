package org.checkstyle.suppressionxpathfilter.finallocalvariable;

public class InputXpathFinalLocalVariable1 {
    public void testMethod() {
        int x; // warn
        x = 3;
    }
}
