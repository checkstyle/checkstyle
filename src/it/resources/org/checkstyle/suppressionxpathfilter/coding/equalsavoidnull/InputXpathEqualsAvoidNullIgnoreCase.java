/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.equalsavoidnull;

public class InputXpathEqualsAvoidNullIgnoreCase {
    public void test() {
        String nullString = null;
        // violation 2 lines below """String literal expressions should be on the
        // left side of an equalsIgnoreCase comparison."""
        nullString.equalsIgnoreCase("Another string"); //warn
    }
}
