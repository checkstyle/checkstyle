/*
UnusedLambdaParameterShouldBeUnnamed


*/


package org.checkstyle.suppressionxpathfilter.coding.unusedlambdaparametershouldbeunnamed;

import java.util.function.Function;

public class InputXpathUnusedLambdaParameterShouldBeUnnamedSimple {

    void test() {
        Function<Integer, Integer> f = (x) -> 1;
        // violation above "Unused lambda parameter 'x' should be unnamed."
    }
}
