package org.checkstyle.suppressionxpathfilter.catchparametername;

import java.util.function.Function;

abstract class SuppressionXpathRegressionCatchParameterNameLambda {
    abstract void abstracMethod();

    private final Function<Integer, Integer> lambdaFunction = a -> {
        Integer i = a;
        for (; i > 10; i--) {
            try {
            } catch (Exception e) { // warn
            }
        }
        return i;
    };
}
