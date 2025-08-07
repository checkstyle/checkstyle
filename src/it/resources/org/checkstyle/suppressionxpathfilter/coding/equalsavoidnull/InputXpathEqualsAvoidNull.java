package org.checkstyle.suppressionxpathfilter.coding.equalsavoidnull;

public class InputXpathEqualsAvoidNull {
    public void test() {
        String nullString = null;
        nullString.equals("Another string"); //warn
    }
}
