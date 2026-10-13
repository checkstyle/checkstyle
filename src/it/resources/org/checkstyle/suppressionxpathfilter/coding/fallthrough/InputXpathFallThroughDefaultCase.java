/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package org.checkstyle.suppressionxpathfilter.coding.fallthrough;

public class InputXpathFallThroughDefaultCase {
    void methodFallThruCustomWords(int i, int j, boolean cond) {
        while (true) {
            switch (i){
                case 0:
                    i++;
                    break;
                default:
                // violation above 'Fall through from the last branch of the switch statement.'
                    i++;
            }
        }
    }
}
