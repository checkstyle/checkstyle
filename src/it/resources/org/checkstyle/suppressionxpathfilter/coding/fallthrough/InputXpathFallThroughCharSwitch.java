/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package org.checkstyle.suppressionxpathfilter.coding.fallthrough;

public class InputXpathFallThroughCharSwitch {
    public void test() {
        char[] v = {'a','b','c'};
        for(char c:v) {
            switch (c) {
                case 'a':
                    System.out.println("a");
                    break;
                case 'b':
                    System.out.println("b");
                case 'c':
                // violation above 'Fall through from previous branch of the switch statement.'
                    System.out.println("c");
                    break;
            }
        }
    }
}
