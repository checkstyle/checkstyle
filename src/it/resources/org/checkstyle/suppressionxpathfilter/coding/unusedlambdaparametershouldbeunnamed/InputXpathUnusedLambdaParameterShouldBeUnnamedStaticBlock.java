/*
UnusedLambdaParameterShouldBeUnnamed


*/


package org.checkstyle.suppressionxpathfilter.coding.unusedlambdaparametershouldbeunnamed;

import java.util.function.Function;

public class InputXpathUnusedLambdaParameterShouldBeUnnamedStaticBlock {
    static {
        Function<Integer, Integer> f = (x) -> 1;
        // violation above "Unused lambda parameter 'x' should be unnamed."
    }
}
