package org.checkstyle.suppressionxpathfilter.booleanexpressioncomplexity;

public class SuppressionXpathRegressionBooleanExpressionComplexityThree {
    public void methodThree() {
        boolean a = true;
        boolean b = false;

        boolean c = (a & b) | (b ^ a); // OK
        boolean d = (a & b) | (b ^ a) | (a ^ b) | // warn
                (a & b) | (b ^ a) | (a ^ b) | (a & b);
    }
}
