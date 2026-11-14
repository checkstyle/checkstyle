/*
com.puppycrawl.tools.checkstyle.checks.coding.DuplicateConditionIfChainCheck
*/

package org.checkstyle.suppressionxpathfilter.coding.duplicateconditionifchain;

public class InputXpathDuplicateConditionIfChain {
    void foo(int x) {
        if (x > 0) {
            System.out.println("positive");
        } else if (x > 0) { // warn
            System.out.println("positive again");
        }
    }
}
