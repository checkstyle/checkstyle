package org.checkstyle.suppressionxpathfilter.coding.expressionoverblocklambda;

import java.util.function.Function;

public class InputXpathExpressionOverBlockLambdaLocalVar {
    void testLocal() {
        Function<Integer, Integer> f =
            x -> { return x + 1; }; // warn
    }
}
