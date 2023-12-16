/*
InnerAssignment

*/
package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;

//non-compiled with javac: Compilable with Java14
public class InputInnerAssignmentWithEnhancedSwitch {

    void method(final String operation) {
        boolean flag = false;
        switch (operation) {
            case "Y" -> flag = true;
            case "N" -> {
                flag = false;
            }
            default -> throw new UnsupportedOperationException();
        }
    }
}
