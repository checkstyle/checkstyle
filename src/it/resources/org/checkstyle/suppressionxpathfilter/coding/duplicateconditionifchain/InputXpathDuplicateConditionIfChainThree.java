/*
com.puppycrawl.tools.checkstyle.checks.coding.DuplicateConditionIfChainCheck
*/

package org.checkstyle.suppressionxpathfilter.coding.duplicateconditionifchain;

public class InputXpathDuplicateConditionIfChainThree {
    void baz(boolean cond1, boolean cond2) {
        if (cond1) {
            System.out.println("c1");
        } else if (cond2) {
            System.out.println("c2");
        } else if (cond1) { // warn
            System.out.println("c1 duplicate");
        }
    }
}
