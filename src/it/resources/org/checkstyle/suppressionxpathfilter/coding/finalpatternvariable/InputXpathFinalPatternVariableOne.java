/*
FinalPatternVariable


*/

package org.checkstyle.suppressionxpathfilter.coding.finalpatternvariable;

public class InputXpathFinalPatternVariableOne {
    public void test(Object obj) {
        // violation below "Pattern variable 's' should be declared final."
        if (obj instanceof String s) {
            System.out.println(s);
        }
    }
}
