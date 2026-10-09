/*
ParameterAssignment


*/


package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentWithEnhancedSwitch {

    int method(int a) {
        switch (a) {
            case 1, 2 -> a = 12; // violation 'Assignment of parameter 'a' is not allowed.'
            default -> throw new IllegalArgumentException("Invalid");
        }
        return a;
    }

    public void validAssign(String result) {
        result = switch ("in") { // violation 'Assignment of parameter 'result' is not allowed.'
            case "correct" -> "true";
            default -> "also correct";
        };
    }

    public void lambdaInSwitchRule() {
        java.util.function.Consumer<Integer> action = switch (1) {
            case 1 -> x -> {
                x = 2; // violation 'Assignment of parameter 'x' is not allowed.'
            };
            default -> null;
        };
        java.util.function.IntUnaryOperator op = switch (1) {
            case 1 -> x -> ++x; // violation 'Assignment of parameter 'x' is not allowed.'
            default -> x -> x;
        };
    }
}
