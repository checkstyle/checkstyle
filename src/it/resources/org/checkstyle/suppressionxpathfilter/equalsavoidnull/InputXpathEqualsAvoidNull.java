package org.checkstyle.suppressionxpathfilter.equalsavoidnull;

public class InputXpathEqualsAvoidNull {
    public void test() {
        String nullString = null;
        nullString.equals("Another string"); //warn
    }
}
