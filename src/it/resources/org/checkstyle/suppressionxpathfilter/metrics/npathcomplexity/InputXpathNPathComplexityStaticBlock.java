/*
NPathComplexity
max = 0


*/

package org.checkstyle.suppressionxpathfilter.metrics.npathcomplexity;

public class InputXpathNPathComplexityStaticBlock {
    static { // violation 'NPath Complexity is 3 (max allowed is 0).'
        int i = 1;
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
            // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }
}
