package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;// violation

import java.util.function.Function;
/* Config:
 * scope = private
 * excludeScope = null
 * ignoreNamePattern = null
 */
public class InputJavadocVariableNoJavadocNeededInLambda {
    private static final Function<String, String> FUNCTION1 = (String it) -> { // violation
        String stuff = it;
        return stuff + it;
    };

    /** */
    private static final Function<String, String> FUNCTION2 = (String it) -> {
        String stuff = it;
        return stuff + it;
    };

    /** Runnable. */
    private Runnable r = () -> {
        String str = "Hello world";
    };
}
