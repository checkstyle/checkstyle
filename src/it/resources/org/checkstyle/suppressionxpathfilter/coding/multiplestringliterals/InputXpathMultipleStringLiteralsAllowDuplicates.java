package org.checkstyle.suppressionxpathfilter.coding.multiplestringliterals;

public class InputXpathMultipleStringLiteralsAllowDuplicates {
    String a = "StringContents";
    public void myTest() {
      String a2 = "StringContents";
      String a3 = "DoubleString" + "DoubleString";
      String a5 = ", " + ", " + ", "; // warn
   }
}
