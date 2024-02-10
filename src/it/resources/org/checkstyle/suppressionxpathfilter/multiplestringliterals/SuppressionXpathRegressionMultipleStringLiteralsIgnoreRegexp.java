package org.checkstyle.suppressionxpathfilter.multiplestringliterals;

public class SuppressionXpathRegressionMultipleStringLiteralsIgnoreRegexp {

    public void myTest() {

      String a3 = "DoubleString" + "DoubleString"; // warn
      String a4 = "SingleString"; // ok
      String a5 = ", " + ", " + ", "; // ok

    }
}
