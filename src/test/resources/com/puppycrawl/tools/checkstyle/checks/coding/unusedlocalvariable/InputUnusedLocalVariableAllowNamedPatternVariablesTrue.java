/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = 21

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableAllowNamedPatternVariablesTrue {
    record Ignored(int x, int y) {}

    String whatClass(Object object) {
        return switch (object) {
            case String ignored -> "A String";
            case Integer ignored2 -> "An Integer";
            default -> "Something Else";
        };
    }

    void method(Object object) {
        int x = 10; // violation 'Unused local variable'
        if (object instanceof String ignored) { // violation 'Unused local variable'
            System.out.println("string");
        }
    }

    void whatClassTraditional(Object object) {
        switch (object) {
            case String ignored:
                if (object instanceof String s){ // violation 'Unused local variable'
                    System.out.println("S");
                }
                System.out.println("String");
                break;
            case Integer ignored2:
                System.out.println("Integer");
                break;
            default:
            break;
        }
    }
    String withrecord(Object object) {
        return switch (object) {
            case Ignored(int y, int z) -> "record switch";
            default -> "other";
        };
    }
}
