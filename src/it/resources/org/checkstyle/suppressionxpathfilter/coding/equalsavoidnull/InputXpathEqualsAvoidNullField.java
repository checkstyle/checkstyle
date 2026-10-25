/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.equalsavoidnull;

public class InputXpathEqualsAvoidNullField {
    String s = "a";
    // violation 2 lines below """String literal expressions should be on
    // the left side of an equals comparison."""
    boolean b = s.equals("test"); // warn
}
