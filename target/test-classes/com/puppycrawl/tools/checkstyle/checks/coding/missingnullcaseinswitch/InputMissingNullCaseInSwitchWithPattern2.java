/*
MissingNullCaseInSwitch

*/

// java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

public class InputMissingNullCaseInSwitchWithPattern2 {
    static void m(Object o) {
        switch (o) {  // violation, 'Switch using reference types should have a null case.'
            case String s when s.length() > 2 -> {
            }
            default -> {
            }
        }
        switch (o) {
            case null -> {}
            case String s -> {}
            default -> {
            }
        }
        switch (o) {
            case null -> {}
            default -> {}
        }
        switch (o) {
            case null -> {
            }
            default -> {
            }
        }
        switch (o) {
            case String s -> {}
            case null -> {}
            default -> {
            }
        }
    }

    void m3(Object o) {
        switch (o) {  // violation, 'Switch using reference types should have a null case.'
            case String s when s.length() > 4 -> {}
            case String s when s.length() > 6 -> {}
            case String s -> {}
            default -> {}
        }
    }

    void m4(Object o) {
        switch (o) {
            case String s when s.length() > 4 -> {}
            case String s when s.length() > 6 -> {}
            case String s -> {}
            case null -> {}
            default -> {}
        }
    }

    void m5(char o) {
        switch (o) {
            case 'a' -> {}
            default -> {}
        }
    }
}
