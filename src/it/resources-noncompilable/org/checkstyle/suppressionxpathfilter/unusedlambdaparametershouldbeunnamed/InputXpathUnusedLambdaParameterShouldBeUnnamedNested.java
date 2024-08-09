//non-compiled with javac: Compilable with Java21
package org.checkstyle.suppressionxpathfilter.unusedcatchparametershouldbeunnamed;

import java.util.function.Function;

public class InputXpathUnusedLambdaParameterShouldBeUnnamedNested {

    void test() {
        Function<Integer, Integer> f1 = (x) -> {
            Function<Integer, Integer> f = (y) -> x; // warn
            return 2;
        };
    }
}
