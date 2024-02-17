package org.checkstyle.suppressionxpathfilter.multiplestringliterals;

public class SuppressionXpathRegressionMultipleStringLiteralsDefault {
    String a = "StringContents"; // warn
    String a1 = "unchecked";

    @SuppressWarnings("unchecked") // ok
    public void myTest() {
      String a2 = "StringContents";
      String a4 = "SingleString";
    }
}
