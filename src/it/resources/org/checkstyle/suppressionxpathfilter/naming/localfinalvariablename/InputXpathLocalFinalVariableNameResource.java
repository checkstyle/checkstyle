package org.checkstyle.suppressionxpathfilter.naming.localfinalvariablename;

import java.util.Scanner;

public class InputXpathLocalFinalVariableNameResource {
    void MyMethod() {
        try(Scanner scanner = new Scanner("ABC")) { // warn
            final int VAR1 = 5;
            final int var1 = 10;
        }
    }
}
