package org.checkstyle.suppressionxpathfilter.coding.finalpatternvariable;

public class InputXpathFinalPatternVariable {
    public void test(Object obj) {
        if (obj instanceof String s) { // warn
            System.out.println(s);
        }
    }
}
