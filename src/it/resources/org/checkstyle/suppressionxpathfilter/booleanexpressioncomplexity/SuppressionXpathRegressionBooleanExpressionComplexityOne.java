package org.checkstyle.suppressionxpathfilter.booleanexpressioncomplexity;

public class SuppressionXpathRegressionBooleanExpressionComplexityOne {
    public void methodOne() {
        boolean a = true;
        boolean b = false;

        boolean c = (a & b) | (b ^ a); // OK
        boolean d = (a & b) | (b ^ a) | (a & b) | (b ^ a); // warn
    }
}
