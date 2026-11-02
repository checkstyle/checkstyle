/*
com.puppycrawl.tools.checkstyle.checks.coding.DuplicateConditionIfChainCheck
*/

package org.checkstyle.suppressionxpathfilter.coding.duplicateconditionifchain;

public class InputXpathDuplicateConditionIfChainTwo {
    void bar(int a, int b) {
        if (a > 0 && b < 10) {
            System.out.println("one");
        } else if (a < 0) {
            System.out.println("two");
        } else if (a > 0 && b < 10) { // warn
            System.out.println("three");
        }
    }
}
