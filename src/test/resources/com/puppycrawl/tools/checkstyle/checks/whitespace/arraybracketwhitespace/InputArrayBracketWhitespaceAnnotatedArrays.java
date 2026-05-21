/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

public class InputArrayBracketWhitespaceAnnotatedArrays {

    private String @MyAnnotation [] names;

    public void method(String @MyAnnotation [] values) {}

    public String @MyAnnotation [] getValues() {
        return new String[] {"a", "b"};
    }
}

@java.lang.annotation.Target(java.lang.annotation.ElementType.TYPE_USE)
@interface MyAnnotation {
}
