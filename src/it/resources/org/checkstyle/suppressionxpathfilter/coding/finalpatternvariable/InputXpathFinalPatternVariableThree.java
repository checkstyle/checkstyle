package org.checkstyle.suppressionxpathfilter.coding.finalpatternvariable;

public class InputXpathFinalPatternVariableThree {
    record Point(int x, int y) {}

    public void test(Object obj) {
        if (obj instanceof Point(int x, final int y)) { // warn
            System.out.println(x);
        }
    }
}
