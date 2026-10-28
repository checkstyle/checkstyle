/*
LocalVariableName
allowOneCharVarInForLoop = (default)false
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

package org.checkstyle.suppressionxpathfilter.naming.localvariablename;

public class InputXpathLocalVariableNameInnerClass {
  public class InnerClass {
    void myMethod() {
      int VAR = 1; // violation 'must match pattern'
    }
  }
}
