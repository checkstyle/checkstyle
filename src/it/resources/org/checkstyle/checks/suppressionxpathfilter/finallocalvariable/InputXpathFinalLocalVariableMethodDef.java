package org.checkstyle.checks.suppressionxpathfilter.finallocalvariable;

public class InputXpathFinalLocalVariableMethodDef {
    public void testMethod() {
        int x; // warn
        x = 3;
    }
}
