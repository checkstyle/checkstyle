/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

public class InputArrayBracketWhitespaceAnnotatedArrays {

    private String @MyAnnotation [] names;

    public void method(String @MyAnnotation [] values) {}

    public String @MyAnnotation [] getValues() {
        return new String[] {"a", "b"};
    }
}

@Target(ElementType.TYPE_USE)
@interface MyAnnotation {
}
