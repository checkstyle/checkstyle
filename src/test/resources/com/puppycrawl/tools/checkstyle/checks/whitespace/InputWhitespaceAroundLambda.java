//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.function.Function;

class InputWhitespaceAroundLambda {
    public void foo() {
        Function<Object, String> function = (o)->o.toString();
    }
}