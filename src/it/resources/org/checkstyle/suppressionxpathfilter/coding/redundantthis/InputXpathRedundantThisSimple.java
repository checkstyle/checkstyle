/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package org.checkstyle.suppressionxpathfilter.coding.redundantthis;

public class InputXpathRedundantThisSimple {
    private String name;

    public void setName(String value) {
        this.name = value;
        // violation above 'Redundant "this", field 'name' can be accessed directly.'
    }
}
