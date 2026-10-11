package org.checkstyle.suppressionxpathfilter.coding.fallthrough;

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
