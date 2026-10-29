/*
CatchParameterName
format = ^[A-Z][a-z]+$


*/

package org.checkstyle.suppressionxpathfilter.naming.catchparametername;

import java.util.function.Function;

abstract class InputXpathCatchParameterNameLambda {
    abstract void abstracMethod();

    private final Function<Integer, Integer> lambdaFunction = a -> {
        Integer i = a;
        for (; i > 10; i--) {
            try {
            } catch (Exception e) { // violation 'Name 'e' must match pattern '^[A-Z][a-z]+$'.'
            }
        }
        return i;
    };
}
