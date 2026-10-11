package org.checkstyle.suppressionxpathfilter.coding.equalsavoidnull;

public class InputXpathEqualsAvoidNullField {
    String s = "a";
    boolean b = s.equals("test"); // warn
}
