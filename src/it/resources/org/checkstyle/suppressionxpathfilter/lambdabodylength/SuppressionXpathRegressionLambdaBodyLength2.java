package org.checkstyle.suppressionxpathfilter.lambdabodylength;

import java.util.function.Function;

public class SuppressionXpathRegressionLambdaBodyLength2 {
    void test() {
        Runnable r = () -> { // warn
            method(2);
            method(3);
            method(4);
            method(5);
        };
    }

    void method(int i) {}
}
