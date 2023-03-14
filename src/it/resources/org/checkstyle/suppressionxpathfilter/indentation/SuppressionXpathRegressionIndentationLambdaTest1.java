package org.checkstyle.suppressionxpathfilter.indentation;

public class SuppressionXpathRegressionIndentationLambdaTest1 {
    void test() {
        MyLambda getA =
        (a) -> a; // warn
    }
}

interface MyLambda {
    int myMethod(int a);
}
