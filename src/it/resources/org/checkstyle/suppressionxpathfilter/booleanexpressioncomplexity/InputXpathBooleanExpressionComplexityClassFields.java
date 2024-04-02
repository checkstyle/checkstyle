package org.checkstyle.suppressionxpathfilter.booleanexpressioncomplexity;

public class InputXpathBooleanExpressionComplexityClassFields {
    public void methodTwo() {
        boolean a = true;
        boolean b = false;

        boolean c = (a & b) | (b ^ a); // OK
        boolean d = (a & b) | (b ^ a) | (a ^ b) | // warn
                (a & b) | (b ^ a) | (a ^ b);
    }
}
