package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.function.Function;

public class InputNoJavadocNeededInLambda {
    private static final Function<String, String> FUNCTION = (String it) -> {
        String stuff = it;
        return stuff + it;
    };
}
