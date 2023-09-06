package org.checkstyle.suppressionxpathfilter.illegaltoken;

public class SuppressionXpathRegressionIllegalToken1 {
  public void myTest() {
    outer: // warn
    for (int i = 0; i < 5; i++) {
      if (i == 1) {
        break outer;
      }
    }
  }
}
