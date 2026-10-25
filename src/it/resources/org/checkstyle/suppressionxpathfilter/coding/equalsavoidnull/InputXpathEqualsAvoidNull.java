/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.equalsavoidnull;

public class InputXpathEqualsAvoidNull {
    public void test() {
        String nullString = null;
        nullString.equals("Another string");
        // violation above """String literal expressions should be
        // on the left side of an equals comparison."""
    }
}
