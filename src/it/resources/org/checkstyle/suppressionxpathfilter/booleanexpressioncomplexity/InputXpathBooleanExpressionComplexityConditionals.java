package org.checkstyle.suppressionxpathfilter.booleanexpressioncomplexity;

public class InputXpathBooleanExpressionComplexityConditionals {
    public void methodThree() {
        boolean a = true;
        boolean b = false;
        boolean c = true;

        if (((a && (b & a)) || (b ^ a))) { // warn
            a = b;
        } else if ((a || b) ^ (a && b)) { // OK
            c = b;
        }
    }
}
