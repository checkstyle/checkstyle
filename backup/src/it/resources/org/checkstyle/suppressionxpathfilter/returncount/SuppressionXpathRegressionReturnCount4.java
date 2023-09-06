package org.checkstyle.suppressionxpathfilter.returncount;

import java.util.function.Function;

public class SuppressionXpathRegressionReturnCount4 {
    void testLambda() {
        Function<Integer, Boolean> a = i -> { // warn
            switch(i) {
            case 1: return true;
            case 2: return true;
            case 3: return true;
            }
            return false;
        };
    }
}
