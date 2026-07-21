/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = 1.21

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableJdkVersionLegacyFormat {
    String whatClass(Object object) {
        return switch (object) {
            case String ignored -> "A String";
            case Integer ignored2 -> "An Integer";
            default -> "Something Else";
        };
    }
}
