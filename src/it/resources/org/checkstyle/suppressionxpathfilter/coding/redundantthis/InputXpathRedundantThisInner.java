/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package org.checkstyle.suppressionxpathfilter.coding.redundantthis;

public class InputXpathRedundantThisInner {
    class Inner {
        private String name;

        public String getName() {
            return this.name;
            // violation above 'Redundant "this", field 'name' can be accessed directly.'
        }
    }
}
