/*
RedundantThis
checkMethods=true
allowAdjacentToRequiredThis=(default)true

*/

package org.checkstyle.suppressionxpathfilter.coding.redundantthis;

public class InputXpathRedundantThisMethodCall {
    private String name;

    public boolean validate() {
        return name.equals("abc");
    }

    public void process() {
        this.validate();
        // violation above 'Redundant "this", method 'validate' can be accessed directly.'
    }
}
