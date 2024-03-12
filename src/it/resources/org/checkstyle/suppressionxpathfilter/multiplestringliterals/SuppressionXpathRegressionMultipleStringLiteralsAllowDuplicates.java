package org.checkstyle.suppressionxpathfilter.multiplestringliterals;

public class SuppressionXpathRegressionMultipleStringLiteralsAllowDuplicates {
    String a = "StringContents"; // ok
    public void myTest() {
      String a2 = "StringContents";
      String a3 = "DoubleString" + "DoubleString"; // ok
      String a5 = ", " + ", " + ", "; // warn
   }
}
