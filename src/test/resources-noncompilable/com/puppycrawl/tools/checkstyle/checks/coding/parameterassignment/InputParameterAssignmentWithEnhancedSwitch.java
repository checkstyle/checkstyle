/*
ParameterAssignment


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentWithEnhancedSwitch {

    int method(int a) {
        switch (a) {
            case 1, 2 -> a = 12; // violation
            default -> throw new IllegalArgumentException("Invalid");
        }
        return a;
    }

    public void validAssign(String result) {
        result = switch ("in") { // violation
            case "correct" -> "true";
            default -> "also correct";
        };
    }
}
