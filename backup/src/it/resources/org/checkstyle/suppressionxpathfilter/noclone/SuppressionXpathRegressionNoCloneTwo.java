package org.checkstyle.suppressionxpathfilter.noclone;

public class SuppressionXpathRegressionNoCloneTwo {

  class InnerClass {
    public Object clone() { return null; } // warn
  }

}
