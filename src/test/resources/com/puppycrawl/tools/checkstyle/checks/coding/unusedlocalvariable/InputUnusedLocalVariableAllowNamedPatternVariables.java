/*
UnusedLocalVariable
allowUnnamedVariables = false
allowNamedPatternVariables = false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAllowNamedPatternVariables {

    String whatClass(Object object) {
        return switch (object) {
            case String ignored -> "A String"; // violation, 'Unused local variable'
            case Integer ignored2 -> "An Integer"; // violation, 'Unused local variable'
            default -> "Something Else";
        };
    }

    void method(Object object) {
        int x = 10; // violation, 'Unused local variable'
        if (object instanceof String ignored) { // violation, 'Unused local variable'
            System.out.println("string");
        }
    }
}
