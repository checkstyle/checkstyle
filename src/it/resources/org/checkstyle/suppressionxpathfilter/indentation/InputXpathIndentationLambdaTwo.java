package org.checkstyle.suppressionxpathfilter.indentation;

interface MyLambdaInterface {
    int foo(int a, int b);
};

public class InputXpathIndentationLambdaTwo {
    void test() {
        MyLambdaInterface div = (a, b)
            -> {
                if(b != 0) {
                    return a/b;
                }
              return 0; // warn
        };
    }
}
