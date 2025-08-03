package org.checkstyle.checks.suppressionxpathfilter.fallthrough;

public class InputXpathFallThroughDefaultCase {
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
