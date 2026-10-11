package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableMethodDef {
    public void testMethod() {
        int x; // warn
        x = 3;
    }
}
