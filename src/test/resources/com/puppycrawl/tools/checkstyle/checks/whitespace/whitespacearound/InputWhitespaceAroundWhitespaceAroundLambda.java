package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

import java.util.function.Function;


class InputWhitespaceAroundWhitespaceAroundLambda {
    public void foo() {
        Function<Object, String> function = (o)->o.toString();
    }
}
