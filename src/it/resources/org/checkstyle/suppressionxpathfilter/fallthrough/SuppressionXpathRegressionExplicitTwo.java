package org.checkstyle.suppressionxpathfilter.fallthrough;

public class SuppressionXpathRegressionExplicitTwo {
    void methodFallThruCustomWords(int i, int j, boolean cond) {
        while (true) {
            switch (i){
                case 0:
                    i++;
                    break;
                default: //warn
                    i++;
            }
        }
    }
}
