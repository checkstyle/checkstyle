/*
LocalFinalVariableName
format = ^[A-Z][a-z0-9]*$
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,RESOURCE


*/

package org.checkstyle.suppressionxpathfilter.naming.localfinalvariablename;

public class InputXpathLocalFinalVariableNameVar {
    void MyMethod() {
        final int VAR1 = 5; // violation 'Name 'VAR1' must match pattern '^[A-Z][a-z0-9]*$'.'
    }
}
