package org.checkstyle.suppressionxpathfilter.indentation;

public class InputXpathIndentationLambdaOne {
    void test() {
        MyLambda getA =
        (a) -> a; // warn
    }
}

interface MyLambda {
    int myMethod(int a);
}
