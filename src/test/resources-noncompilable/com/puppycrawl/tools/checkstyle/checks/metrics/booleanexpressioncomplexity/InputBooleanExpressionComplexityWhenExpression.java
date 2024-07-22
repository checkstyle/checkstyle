/*
BooleanExpressionComplexity
max = 0
tokens = (default)LAND, BAND, LOR, BOR, BXOR


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityWhenExpression {

    void test(Object obj) {
        switch (obj) {
            case ColoredPoint(boolean a, boolean b, boolean _)
                    when (a ^ (a || b) ^ (b || a) & (a | b)) -> {
                // violation above, 'Boolean expression complexity is 6*.'

                // violation below, 'Boolean expression complexity is 6*.'
                if (a ^ (a || b) ^ (b || a) & (a | b)) {
                }

                // violation below, 'Boolean expression complexity is 6*.'
                boolean c = (a ^ (a || b) ^ (b || a) & (a | b));

            }
            // violation below, 'Boolean expression complexity is 6*.'
            case ColoredPoint(boolean a, _, _) when a && a -> { }
            default -> System.out.println("none");
        }
    }

    record ColoredPoint(boolean p, boolean x, boolean c) { }
}
