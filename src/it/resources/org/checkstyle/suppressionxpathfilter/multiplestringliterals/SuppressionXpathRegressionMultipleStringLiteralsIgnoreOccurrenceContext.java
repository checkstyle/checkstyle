package org.checkstyle.suppressionxpathfilter.multiplestringliterals;

public class SuppressionXpathRegressionMultipleStringLiteralsIgnoreOccurrenceContext {
    String a = "StringContents";
    String a1 = "unchecked"; // warn

    @SuppressWarnings("unchecked")
    public void myTest() {
       String a3 = "DoubleString"; // ok
       String a5 = "unchecked";
    }
}
