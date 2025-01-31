package org.checkstyle.suppressionxpathfilter.multiplestringliterals;

public class InputXpathMultipleStringLiteralsIgnoreOccurrenceContext {
    String a = "StringContents";
    String a1 = "unchecked"; // warn

    @SuppressWarnings("unchecked")
    public void myTest() {
       String a3 = "DoubleString";  
       String a5 = "unchecked";
    }
}
