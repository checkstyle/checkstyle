/*
InnerAssignment


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;

public class InputInnerAssignmentWithEnhancedSwitch {

    void method(final String operation) {
        boolean flag = false;
        if (flag = false) {} // violation
        switch (operation) {
            case "Y" -> flag = true;  // no violation 
            case "N" -> {
                flag = false;
            }
            default -> throw new UnsupportedOperationException();
        }
    }
}
