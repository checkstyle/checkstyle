/*
JavadocVariable
accessModifiers = (default)private
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

import java.util.function.Function;

public class InputJavadocVariableNoJavadocNeededInLambda {
    // violation below, 'Missing a Javadoc comment'
    private static final Function<String, String> FUNCTION1 = (String it) -> {
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
