package org.checkstyle.suppressionxpathfilter.coding.finalpatternvariable;

public class InputXpathFinalPatternVariableTwo {
    public void test(Object obj) {
        if (obj instanceof Integer i) { // warn
            System.out.println(i);
        }
    }
}
