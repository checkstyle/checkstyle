package org.checkstyle.suppressionxpathfilter.booleanexpressioncomplexity;

public class SuppressionXpathRegressionBooleanExpressionComplexityOne {
    public boolean methodOne() {
        boolean a = true;
        boolean b = false;
        try {
            a = b;
        } catch(Exception e) {
            boolean d = (a & b) | (b ^ a) | (a ^ b) | // warn
                    (a & b) | (b ^ a) | (a ^ b);
        }
        return true;
    }
}
