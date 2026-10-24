/*
LambdaBodyLength
max = 5


*/

package org.checkstyle.suppressionxpathfilter.sizes.lambdabodylength;

import java.util.function.Function;

public class InputXpathLambdaBodyLengthCustomMax {
    void test() {
        // violation below 'Lambda body length is 6 lines (max allowed is 5).'
        Runnable r = () -> {
            method(2);
            method(3);
            method(4);
            method(5);
        };
    }

    void method(int i) {}
}
