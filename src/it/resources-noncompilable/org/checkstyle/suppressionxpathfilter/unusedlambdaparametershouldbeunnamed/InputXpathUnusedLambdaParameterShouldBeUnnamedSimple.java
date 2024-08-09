//non-compiled with javac: Compilable with Java21
package org.checkstyle.suppressionxpathfilter.unusedcatchparametershouldbeunnamed;

import java.util.function.Function;

public class InputXpathUnusedLambdaParameterShouldBeUnnamedSimple {

    void test() {
        Function<Integer, Integer> f = (x) -> 1; // warn
    }
}
