/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.equalsavoidnull;

public class InputXpathEqualsAvoidNullIgnoreCase {
    public void test() {
        String nullString = null;
        nullString.equalsIgnoreCase("Another string");
        // violation above """String literal expressions should be
        // on the left side of an equalsIgnoreCase comparison."""
    }
}
