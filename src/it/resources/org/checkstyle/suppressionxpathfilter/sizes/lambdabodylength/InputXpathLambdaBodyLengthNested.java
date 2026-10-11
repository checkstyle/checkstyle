package org.checkstyle.suppressionxpathfilter.sizes.lambdabodylength;

public class InputXpathLambdaBodyLengthNested {
    void test() {
        Runnable r = () -> { // warn
            int x = 0;
            if (x > 0) {
                x = x * 2;
                System.out.println(x);
            } else {
                x = x + 1;
                System.out.println(x + 1);
            }
            method3();
        };
    }

    void method3() {}
}
