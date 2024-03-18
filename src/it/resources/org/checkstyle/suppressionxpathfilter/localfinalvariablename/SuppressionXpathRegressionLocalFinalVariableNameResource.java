package org.checkstyle.suppressionxpathfilter.localfinalvariablename;

import java.util.Scanner;

public class SuppressionXpathRegressionLocalFinalVariableNameResource {
    void MyMethod() {
        try(Scanner scanner = new Scanner("ABC")) { // warn
            final int VAR1 = 5;
            final int var1 = 10;
        }
    }
}
