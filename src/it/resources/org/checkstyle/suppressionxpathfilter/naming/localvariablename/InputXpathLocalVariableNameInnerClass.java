package org.checkstyle.suppressionxpathfilter.naming.localvariablename;

public class InputXpathLocalVariableNameInnerClass {
  public class InnerClass {
    void myMethod() {
      int VAR = 1; // warn
    }
  }
}
