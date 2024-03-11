package org.checkstyle.suppressionxpathfilter.equalsavoidnull;

public class InputXpathEqualsAvoidNullIgnoreCase {
    public void test() {
        String nullString = null;
        nullString.equalsIgnoreCase("Another string"); //warn
    }
}
