/*
FinalPatternVariable


*/

package org.checkstyle.suppressionxpathfilter.coding.finalpatternvariable;

public class InputXpathFinalPatternVariableTwo {
    public void test(Object obj) {
        // violation below "Pattern variable 'i' should be declared final."
        if (obj instanceof Integer i) {
            System.out.println(i);
        }
    }
}
