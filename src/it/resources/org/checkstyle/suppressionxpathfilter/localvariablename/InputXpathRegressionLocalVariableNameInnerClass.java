package org.checkstyle.suppressionxpathfilter.localvariablename;

public class InputXpathRegressionLocalVariableNameInnerClass {
  public class InnerClass {
    void myMethod() {
      int VAR = 1; // warn
    }
  }
}
