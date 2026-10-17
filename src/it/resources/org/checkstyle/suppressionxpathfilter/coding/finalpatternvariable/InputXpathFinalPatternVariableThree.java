/*
FinalPatternVariable


*/

package org.checkstyle.suppressionxpathfilter.coding.finalpatternvariable;

public class InputXpathFinalPatternVariableThree {
    record Point(int x, int y) {}

    public void test(Object obj) {
        // violation below "Pattern variable 'x' should be declared final."
        if (obj instanceof Point(int x, final int y)) {
            System.out.println(x);
        }
    }
}
