/*
ExpressionOverBlockLambda


*/

package org.checkstyle.suppressionxpathfilter.coding.expressionoverblocklambda;

import java.util.function.Function;

public class InputXpathExpressionOverBlockLambdaLocalVar {
    void testLocal() {
        Function<Integer, Integer> f =
            // violation below 'Expression lambdas are preferred over single-line block lambdas.'
            x -> { return x + 1; };
    }
}
