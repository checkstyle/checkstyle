/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = (default)22

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariablePatternVariables2 {

    void unusedGuardedPattern(Object obj) {
        switch (obj) {
            case Integer i when 1 > 0 -> System.out.println("positive"); // violation 'i'
            default                   -> System.out.println("other");
        }
    }

    void usedInGuardedPattern(Object obj) {
        switch (obj) {
            case Integer i when i > 0 -> System.out.println(i);
            default                   -> System.out.println("other");
        }
    }

    void unusedInColonCase(Object obj) {
        switch (obj) {
            case String s: // violation, Unused local variable 's'
                System.out.println("string");
                break;
            default:
                break;
        }
    }

    void usedInColonCase(Object obj) {
        switch (obj) {
            case String s:
                System.out.println(s);
                break;
            default:
                break;
        }
    }

    void unusedInstanceof(Object obj) {
        if (obj instanceof String s) { // violation, Unused local variable 's'
            System.out.println("it is a string");
        }
    }

    void usedInstanceof(Object obj) {
        if (obj instanceof String s) {
            System.out.println(s);
        }
    }

    class Test {
        Object field = (Object) (true ? "" : 1) instanceof String s
            ? s
            : null;
    }
}
