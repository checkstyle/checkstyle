package org.checkstyle.suppressionxpathfilter.indentation;

public class InputXpathIndentationLambdaTest1 {
    void test() {
        MyLambda getA =
        (a) -> a; // warn
    }
}

interface MyLambda {
    int myMethod(int a);
}
