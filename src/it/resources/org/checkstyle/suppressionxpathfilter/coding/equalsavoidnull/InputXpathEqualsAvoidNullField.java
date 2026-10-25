/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.equalsavoidnull;

public class InputXpathEqualsAvoidNullField {
    String s = "a";
    boolean b = s.equals("test");
    // violation above """String literal expressions should be
    // on the left side of an equals comparison."""
}
