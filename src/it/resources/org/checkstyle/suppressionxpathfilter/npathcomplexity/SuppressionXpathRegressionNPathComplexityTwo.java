package org.checkstyle.suppressionxpathfilter.npathcomplexity;

public class SuppressionXpathRegressionNPathComplexityTwo {
    static { //warn
        int i = 1;
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
            // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }
}
