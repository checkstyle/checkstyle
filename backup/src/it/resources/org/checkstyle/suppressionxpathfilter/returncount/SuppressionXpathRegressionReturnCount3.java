package org.checkstyle.suppressionxpathfilter.returncount;

public class SuppressionXpathRegressionReturnCount3 {
    SuppressionXpathRegressionReturnCount3() { // warn
        int i = 1;
        switch(i) {
        case 1: return;
        case 2: return;
        case 3: return;
        case 4: return;
        }
        return;
    }
}
