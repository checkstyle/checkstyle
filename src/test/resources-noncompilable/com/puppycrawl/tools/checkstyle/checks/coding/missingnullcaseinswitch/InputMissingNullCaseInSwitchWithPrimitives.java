/*
MissingNullCaseInSwitch

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

public class InputMissingNullCaseInSwitchWithPattern {
    void testIntegers(int obj) {
        switch (obj) {   // ok, case labels are permitive types
            default -> {}
            case 1 + 4 -> {}
            case 2 -> {}
        }
        switch (obj) {
           // case null -> {} // this will not compile
            case 1 + 4 -> {}
            case 2 -> {}
            default -> {}
        }
        switch (obj) {
            case 1 :{}
            case 2: {}
            default :{}
        }
    }

    void testChar(char c) {
        switch (c) {   // ok, case labels are permitive types
            default -> {}
            case 'a' + 'c' -> {}
            case 'b' -> {}
        }
        switch (c) {
           // case null -> {} // this will not compile
            case 'a' -> {}
            case 'b' -> {}
            default -> {}
        }
        switch (c) {
            case 'a' :{}
            case 'b': {}
            default :{}
        }
    }
    void testLocalVariablesAsCaseLabel(int obj) {
        final int x = 1;
        final int y = 0;
        switch (obj) {   // ok, case labels are idents we ignore it
            default -> {}
            case x -> {}
            case y -> {}
        }
        switch (obj) {
           // case null -> {}
            case x -> {}
            case y -> {}
            default -> {}
        }
        switch (obj) {
            case x :{}
            case y: {}
            default :{}
        }
    }

    void boxedTypes(Integer i) {
        switch (i) {   // case label are of a refetence type (Integer) but we can't detect
            default -> {}
            case 1 -> {}
            case 2 -> {}
        }

        switch (i) {
            case null -> {} // this compiles,
            default -> {}
            case 1 -> {}
            case 2 -> {}
        }

    }
}