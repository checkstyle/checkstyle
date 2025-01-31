package org.checkstyle.suppressionxpathfilter.multiplestringliterals;

public class InputXpathMultipleStringLiteralsDefault {
    String a = "StringContents"; // warn
    String a1 = "unchecked";

    @SuppressWarnings("unchecked")  
    public void myTest() {
      String a2 = "StringContents";
      String a4 = "SingleString";
    }
}
