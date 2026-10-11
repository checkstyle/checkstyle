package org.checkstyle.suppressionxpathfilter.whitespace.whitespacearound;

import java.util.function.Function;

public class InputXpathWhitespaceAroundLambda {
    public void foo() {
        Function<Object, String> function = (o)-> o.toString(); //warn
    }
}
