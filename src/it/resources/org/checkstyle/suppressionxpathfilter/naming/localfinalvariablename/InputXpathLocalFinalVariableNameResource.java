/*
LocalFinalVariableName
format = ^[A-Z][A-Z0-9]*$
tokens = PARAMETER_DEF, RESOURCE


*/

package org.checkstyle.suppressionxpathfilter.naming.localfinalvariablename;

import java.util.Scanner;

public class InputXpathLocalFinalVariableNameResource {
    void MyMethod() {
        // violation below 'Name 'scanner' must match pattern '^[A-Z][A-Z0-9]*$'.'
        try(Scanner scanner = new Scanner("ABC")) {
            final int VAR1 = 5;
            final int var1 = 10;
        }
    }
}
