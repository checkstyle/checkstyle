package org.checkstyle.checks.suppressionxpathfilter.multiplestringliterals;

public class InputXpathMultipleStringLiteralsDefault {
    String a = "StringContents"; // warn
    String a1 = "unchecked";

    @SuppressWarnings("unchecked") // ok
    public void myTest() {
      String a2 = "StringContents";
      String a4 = "SingleString";
    }
}
