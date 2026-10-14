/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package org.checkstyle.suppressionxpathfilter.coding.fallthrough;

public class InputXpathFallThrough {
    public void test() {
        int id = 0;
        switch (id) {
            case 0: break;
            case 1: if (1 == 0) {
                break;
            };
            case 2: break;
            // violation above 'Fall through from previous branch of the switch statement.'
        }
    }
}
