package org.checkstyle.suppressionxpathfilter.coding.equalsavoidnull;

public record InputXpathEqualsAvoidNullRecord(String s) {
    public void test() {
        s.equals("test"); // warn
    }
}
