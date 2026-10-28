/*
LocalVariableName
allowOneCharVarInForLoop = (default)false
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

package org.checkstyle.suppressionxpathfilter.naming.localvariablename;

public class InputXpathLocalVariableNameMethod {
  void MyMethod() {
    int VAR = 1; // violation 'must match pattern'
  }
}
